package com.github.driversti.erepublik.friendsadd;

public interface ApiClient {

  void addFriend(AddFriendRequestConfig addFriendRequestConfig);

  Player getCitizen(GetCitizenRequestConfig getCitizenRequestConfig);
}
