package com.github.driversti.erepublik.friendsadd;

import java.net.http.HttpClient;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FriendsAddApp {

  private static final Logger log = LogManager.getLogger(FriendsAddApp.class);

  public static void main(String[] args) {
    Map<ArgumentKey, String> argumentsMap = new ArgumentParser().parse(args);

    HttpClient httpClient = HttpClient.newHttpClient();
    EreptoolsApiClient ereptoolsApiClient = new DefaultEreptoolsApiClient(httpClient);
    JobConfig jobConfig = new JobConfigFactory(ereptoolsApiClient).create(argumentsMap);
    ErepublikApiClient erepublikApiClient = new DefaultErepublikApiClient(httpClient);

    new Runner(erepublikApiClient).run(jobConfig);
  }
}
