package com.github.driversti.erepublik.friendsadd;

import com.github.driversti.erepublik.friendsadd.RequestConfig.Builder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Runner extends Thread {

  private static final Logger log = LogManager.getLogger(Runner.class);
  private final ApiClient apiClient;

  public Runner(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  void run(JobConfig jc) {
    for (int citizenId = jc.fromId(); citizenId <= jc.toId(); citizenId++) {
      // TODO: extend!
      RequestConfig requestConfig = new Builder(jc.erpk(), jc.token(), citizenId).build();
      apiClient.addFriend(requestConfig);
      waitIfNotLastCitizenId(citizenId, jc.toId());
    }
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
