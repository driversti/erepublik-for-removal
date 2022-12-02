package com.github.driversti.erepublik.friendsadd;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

import com.github.driversti.erepublik.friendsadd.clients.ErepublikApiClient;
import com.github.driversti.erepublik.friendsadd.clients.Player;
import com.github.driversti.erepublik.friendsadd.jobconfigs.AddFriendRequestConfig;
import com.github.driversti.erepublik.friendsadd.jobconfigs.Country;
import com.github.driversti.erepublik.friendsadd.jobconfigs.GetCitizenRequestConfig;
import com.github.driversti.erepublik.friendsadd.jobconfigs.JobConfig;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Runner extends Thread {

  private static final Logger log = LogManager.getLogger(Runner.class);
  private final ErepublikApiClient erepublikApiClient;

  public Runner(ErepublikApiClient erepublikApiClient) {
    this.erepublikApiClient = erepublikApiClient;
  }

  void run(JobConfig jc) {
    logJobConfig(jc);
    for (int citizenId = jc.fromId(); citizenId <= jc.toId(); citizenId++) {
      Player player = erepublikApiClient.getCitizen(
          new GetCitizenRequestConfig(jc.erpk(), jc.token(), citizenId));
      // TODO: do not send request if the player is in friend-list already
      if (player == null) {
        log.info("Player with ID [{}] not found. Skipping...", citizenId);
        waitIfNotLastCitizenId(citizenId, jc.toId());
        continue;
      }
      if (!isAmongAllowedCountries(player, jc.includedCountries())) {
        log.info("\"{}\" [{}] is not a citizen [{}] of allowed countries {}. Skipping...",
            player.nickname(), citizenId, player.citizenship(), jc.includedCountries());
        waitIfNotLastCitizenId(citizenId, jc.toId());
        continue;
      }
      if (isAmongCountries(player, jc.excludedCountries())) {
        log.info("\"{}\" [{}] is a citizen [{}] of excluded countries {}. Skipping...",
            player.nickname(), citizenId, player.citizenship(), jc.excludedCountries());
        waitIfNotLastCitizenId(citizenId, jc.toId());
        continue;
      }
      if (isBlocked(jc, player)) {
        log.info("\"{}\" [{}] is blocked. Skipping...", player.nickname(), citizenId);
        waitIfNotLastCitizenId(citizenId, jc.toId());
        continue;
      }
      if (isBanned(jc, player)) {
        log.info("\"{}\" [{}] is banned. Skipping...", player.nickname(), citizenId);
        waitIfNotLastCitizenId(citizenId, jc.toId());
        continue;
      }
      if (isDead(jc, player)) {
        log.info("\"{}\" [{}] is dead. Skipping...", player.nickname(), citizenId);
        waitIfNotLastCitizenId(citizenId, jc.toId());
        continue;
      }
      AddFriendRequestConfig addFriendRequestConfig = new AddFriendRequestConfig
          .Builder(jc.erpk(), jc.token(),
          citizenId).build();
      erepublikApiClient.addFriend(addFriendRequestConfig);
      addLogAfterSentRequest(jc, player);
      waitIfNotLastCitizenId(citizenId, jc.toId());
    }
  }

  private void logJobConfig(JobConfig jc) {
    String allowedCountries = convertCountriesToString(jc.includedCountries());
    if ("[]".equals(allowedCountries)) {
      allowedCountries = "[ALL]";
    }
    String forbiddenCountries = convertCountriesToString(jc.excludedCountries());

    log.info("Got job for adding citizens with IDs between {} and {}, "
            + "from countries {}, except countries {}, "
            + "add blocked citizens: {}, add dead citizens: {}, "
            + "add banned citizens: {}",
        jc.fromId(), jc.toId(), allowedCountries, forbiddenCountries, jc.addBlocked(),
        jc.addDead(), jc.addBanned());
  }

  private String convertCountriesToString(Collection<Country> countries) {
    return countries.stream().map(Country::readableName).sorted()
        .collect(joining(",", "[", "]"));
  }

  private boolean isAmongCountries(Player player, Set<Country> countries) {
    return player.isCitizenOf(countries.stream().map(Country::getId).collect(Collectors.toSet()));
  }

  private boolean isAmongAllowedCountries(Player player, Set<Country> countries) {
    return countries.isEmpty() || isAmongCountries(player, countries);
  }

  private void waitIfNotLastCitizenId(int currentCitizenId, int lastCitizenId) {
    if (currentCitizenId < lastCitizenId) {
      try {
        // allowed 3000 requests per 3600 seconds (1 request each 1200ms).
        // Since we perform 2 request for one citizen, we have to double the delay to avoid 429
        Thread.sleep(2200L);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private static void addLogAfterSentRequest(JobConfig jc, Player player) {
    StringBuilder builder = new StringBuilder();
    builder.append(format("Friendship request has been sent to \"%s\" [%s] %s",
        player.nickname(), player.id(), player.citizenship().readableName()));
    if (isDead(jc, player)) {
      builder.append(", is dead");
    }
    if (isBlocked(jc, player)) {
      builder.append(", is blocked by you");
    }
    if (isBanned(jc, player)) {
      builder.append(", is banned");
    }
    log.info(builder.toString());
  }

  private static boolean isDead(JobConfig jc, Player player) {
    return player.isDead() && !jc.addDead();
  }

  private static boolean isBanned(JobConfig jc, Player player) {
    return player.isBanned() && !jc.addBanned();
  }

  private static boolean isBlocked(JobConfig jc, Player player) {
    return player.isBlocked() && !jc.addBlocked();
  }
}
