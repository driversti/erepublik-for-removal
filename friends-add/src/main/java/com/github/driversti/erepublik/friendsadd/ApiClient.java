package com.github.driversti.erepublik.friendsadd;

import java.util.Set;

public interface ApiClient {

  void addFriend(RequestConfig requestConfig, int citizenId);
  void addFriend(RequestConfig requestConfig, Set<Integer> citizenIds);
}
