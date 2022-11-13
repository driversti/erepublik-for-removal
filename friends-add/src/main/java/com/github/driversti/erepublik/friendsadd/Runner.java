package com.github.driversti.erepublik.friendsadd;

import static java.util.stream.Collectors.joining;

import com.github.driversti.erepublik.friendsadd.AddFriendRequestConfig.Builder;
import java.util.Collection;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Runner extends Thread {

  private static final Logger log = LogManager.getLogger(Runner.class);
  private final ApiClient apiClient;

  public Runner(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  void run(JobConfig jc) {
    logJobConfig(jc);
    for (int citizenId = jc.fromId(); citizenId <= jc.toId(); citizenId++) {
      Player player = apiClient.getCitizen(
          new GetCitizenRequestConfig(jc.erpk(), jc.token(), citizenId));
      if (player.isBanned()) {
        log.info("Player {} with ID {} is banned. Skipping...", player.nickname(), citizenId);
        continue;
      }
      // TODO: add citizens of specified countries!
      if (isAmongCountries(player.citizenship(), jc.excludedCountries())) {
        log.info("{} is citizen of excluded country {}",
            player.nickname(), jc.excludedCountries());
        continue;
      }
      if (player.isBlocked()) {
        log.info("Player {} with ID {} is blocked. Skipping...", player.nickname(), citizenId);
        continue;
      }
      if (player.isDead()) {
        log.info("Player {} with ID {} is dead. Skipping...", player.nickname(), citizenId);
        continue;
      }
      AddFriendRequestConfig addFriendRequestConfig = new Builder(jc.erpk(), jc.token(),
          citizenId).build();
      apiClient.addFriend(addFriendRequestConfig);
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
            + "skipping blocked citizens: {}, skipping dead citizens: {}",
        jc.fromId(), jc.toId(), allowedCountries, forbiddenCountries, jc.addBlocked(),
        jc.addDead());
  }

  private String convertCountriesToString(Collection<Country> countries) {
    return countries.stream().map(Country::readableName).sorted()
        .collect(joining(",", "[", "]"));
  }

  private boolean isAmongCountries(Country country, Set<Country> excludedCountries) {
    return excludedCountries.contains(country);
  }

  private void waitIfNotLastCitizenId(int currentCitizenId, int lastCitizenId) {
    if (currentCitizenId < lastCitizenId) {
      try {
        // allowed 3000 requests per 3600 seconds (1 request each 1200ms).
        // Since we perform 2 request for one citizen, we have to double the delay to avoid 429
        Thread.sleep(2500L);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
