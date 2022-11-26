package com.github.driversti.erepublik.friendsadd;

import com.github.driversti.erepublik.friendsadd.argsparser.ArgumentKey;
import com.github.driversti.erepublik.friendsadd.argsparser.ArgumentParser;
import com.github.driversti.erepublik.friendsadd.clients.DefaultEreptoolsApiClient;
import com.github.driversti.erepublik.friendsadd.clients.DefaultErepublikApiClient;
import com.github.driversti.erepublik.friendsadd.clients.EreptoolsApiClient;
import com.github.driversti.erepublik.friendsadd.clients.ErepublikApiClient;
import com.github.driversti.erepublik.friendsadd.jobconfigs.JobConfig;
import com.github.driversti.erepublik.friendsadd.jobconfigs.JobConfigFactory;
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
