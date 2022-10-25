package com.github.driversti.erepublik.friendsadd;

import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FriendsAddApp {

  private static final Logger log = LogManager.getLogger(FriendsAddApp.class);

  public static void main(String[] args) {
    Map<ArgumentKey, String> argumentsMap = new ArgumentParser().parse(args);

    JobConfig jobConfig = new JobConfigFactory().create(argumentsMap);
    ApiClient apiClient = new DefaultApiClient();

    new Runner(apiClient).run(jobConfig);
  }
}
