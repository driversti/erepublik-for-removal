package com.github.driversti.erepublik.friendsadd;

import java.util.Objects;
import java.util.Set;

public class RequestConfig {

  private final String erpk;
  private final String token;
  private int fromId = 178;
  // todo load the latest ID from https://erepublik.tools/en/society/citizen-registration/0/2022-10-16/2022-10-17/1
  private int toId = 9671074; // on day 2022-10-17
  private Set<Country> includedCountries = Set.of();
  private Set<Country> excludedCountries = Set.of();
  private boolean addBlocked = true; // FIXME make false after testing
  private boolean addDead = true; // FIXME make false after testing

  public RequestConfig(String erpk, String token) {
    this.erpk = erpk;
    this.token = token;
  }

  private RequestConfig(Builder builder) {
    this.erpk = builder.erpk;
    this.token = builder.token;
    this.fromId = builder.fromId;
    this.toId = builder.toId;
    this.includedCountries = builder.includedCountries;
    this.excludedCountries = builder.excludedCountries;
    this.addBlocked = builder.addBlocked;
    this.addDead = builder.addDead;
  }

  String erpk() {
    return erpk;
  }

  String token() {
    return token;
  }

  int fromId() {
    return fromId;
  }

  int toId() {
    return toId;
  }

  Set<Country> includedCountries() {
    return includedCountries;
  }

  Set<Country> excludedCountries() {
    return excludedCountries;
  }

  boolean addBlocked() {
    return addBlocked;
  }

  boolean addDead() {
    return addDead;
  }

  @Override
  public int hashCode() {
    return Objects.hash(erpk, token, fromId, toId, includedCountries, excludedCountries,
        addBlocked, addDead);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RequestConfig that)) {
      return false;
    }
    return fromId == that.fromId && toId == that.toId && addBlocked == that.addBlocked
        && addDead == that.addDead && erpk.equals(
        that.erpk) && token.equals(that.token) && Objects.equals(includedCountries,
        that.includedCountries) && Objects.equals(excludedCountries,
        that.excludedCountries);
  }

  @Override
  public String toString() {
    return "RequestConfig{" +
        "erpk='" + erpk + '\'' +
        ", token='" + token + '\'' +
        ", fromId=" + fromId +
        ", toId=" + toId +
        ", includedCountries=" + includedCountries +
        ", excludedCountries=" + excludedCountries +
        ", addBlocked=" + addBlocked +
        ", addDead=" + addDead +
        '}';
  }

  public static class Builder {

    private final String erpk;
    private final String token;
    private int fromId = 178;
    private int toId = 9671074;
    private Set<Country> includedCountries = Set.of();
    private Set<Country> excludedCountries = Set.of();
    private boolean addBlocked;
    private boolean addDead;

    public Builder(String erpk, String token) {
      this.erpk = erpk;
      this.token = token;
    }

    public Builder fromId(int fromId) {
      this.fromId = fromId;
      return this;
    }

    public Builder toId(int toId) {
      this.toId = toId;
      return this;
    }

    public Builder includedCountries(Set<Country> includedCountries) {
      this.includedCountries = includedCountries;
      return this;
    }

    public Builder excludedCountries(Set<Country> excludedCountries) {
      this.excludedCountries = excludedCountries;
      return this;
    }

    public Builder addBlocked(boolean addBlocked) {
      this.addBlocked = addBlocked;
      return this;
    }

    public Builder addDead(boolean addDead) {
      this.addDead = addDead;
      return this;
    }

    public RequestConfig build() {
      return new RequestConfig(this);
    }
  }
}
