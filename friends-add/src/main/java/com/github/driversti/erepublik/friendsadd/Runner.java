package com.github.driversti.erepublik.friendsadd;

import static java.util.stream.Collectors.joining;

import com.github.driversti.erepublik.friendsadd.RequestConfig.Builder;
import java.util.Collection;
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
      // TODO: skip if banned
      // TODO: add citizens of specified countries!
      // TODO: skip citizens of specified countries!
      // TODO: add/skip blocked citizens
      // TODO: add/skip dead citizens
      RequestConfig requestConfig = new Builder(jc.erpk(), jc.token(), citizenId).build();
      apiClient.addFriend(requestConfig);
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
        jc.fromId(), jc.toId(), allowedCountries, forbiddenCountries, jc.addBlocked(), jc.addDead());
  }

  private String convertCountriesToString(Collection<Country> countries) {
    return countries.stream().map(Country::readableName).sorted()
        .collect(joining(",", "[", "]"));
  }

  private void waitIfNotLastCitizenId(int currentCitizenId, int lastCitizenId) {
    if (currentCitizenId < lastCitizenId) {
      try {
        Thread.sleep(2000L);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
