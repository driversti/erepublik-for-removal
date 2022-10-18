package com.github.driversti.erepublik.friendsadd;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FriendsAddApp {

  private static final Logger log = LogManager.getLogger(FriendsAddApp.class);

  public static void main(String[] args) {
    Map<ArgumentKey, String> argumentsMap = new ArgumentParser().parse(args);

    RequestConfig requestConfig = new RequestConfigFactory().create(argumentsMap);
    Set<Integer> citizensIds = IntStream.rangeClosed(250, 260)
        .boxed().collect(Collectors.toSet());
    ApiClient apiClient = new DefaultApiClient();
    apiClient.addFriend(requestConfig, citizensIds);
  }
}
