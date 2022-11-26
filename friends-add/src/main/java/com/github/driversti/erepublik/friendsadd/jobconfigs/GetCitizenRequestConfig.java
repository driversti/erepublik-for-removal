package com.github.driversti.erepublik.friendsadd.jobconfigs;

import java.util.Objects;

public class GetCitizenRequestConfig {

  private final String erpk;
  private final String token;
  private final int citizenId;

  public GetCitizenRequestConfig(String erpk, String token, Integer citizenId) {
    Objects.requireNonNull(erpk, "erpk cannot be null");
    Objects.requireNonNull(token, "token cannot be null");
    Objects.requireNonNull(citizenId, "citizenId cannot be null");
    this.erpk = erpk;
    this.token = token;
    this.citizenId = citizenId;
  }

  public String erpk() {
    return erpk;
  }

  public String token() {
    return token;
  }

  public int citizenId() {
    return citizenId;
  }
}
