package com.github.driversti.erepublik.friendsadd;

public interface ErepublikApiClient {

  void addFriend(AddFriendRequestConfig addFriendRequestConfig);

  Player getCitizen(GetCitizenRequestConfig getCitizenRequestConfig);
}
