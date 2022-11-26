package com.github.driversti.erepublik.friendsadd.clients;

import com.github.driversti.erepublik.friendsadd.jobconfigs.AddFriendRequestConfig;
import com.github.driversti.erepublik.friendsadd.jobconfigs.GetCitizenRequestConfig;

public interface ErepublikApiClient {

  void addFriend(AddFriendRequestConfig addFriendRequestConfig);

  Player getCitizen(GetCitizenRequestConfig getCitizenRequestConfig);
}
