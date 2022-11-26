package com.github.driversti.erepublik.friendsadd;

import java.util.Objects;
import java.util.Set;

public class AddFriendRequestConfig {

  private final String erpk;
  private final String token;
  private final int citizenId;
  private Set<Country> includedCountries;
  private Set<Country> excludedCountries;
  private boolean addBlocked;
  private boolean addDead;

  private AddFriendRequestConfig(Builder builder) {
    this.erpk = builder.erpk;
    this.token = builder.token;
    this.citizenId = builder.citizenId;
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

  int citizenId() {
    return citizenId;
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
    return Objects.hash(erpk, token, citizenId, includedCountries, excludedCountries,
        addBlocked, addDead);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AddFriendRequestConfig that)) {
      return false;
    }
    return citizenId == that.citizenId && addBlocked == that.addBlocked && addDead == that.addDead
        && erpk.equals(that.erpk) && token.equals(that.token)
        && Objects.equals(includedCountries, that.includedCountries)
        && Objects.equals(excludedCountries, that.excludedCountries);
  }

  @Override
  public String toString() {
    return "JobConfig{" +
        "erpk='" + erpk + '\'' +
        ", token='" + token + '\'' +
        ", citizenId=" + citizenId +
        ", includedCountries=" + includedCountries +
        ", excludedCountries=" + excludedCountries +
        ", addBlocked=" + addBlocked +
        ", addDead=" + addDead +
        '}';
  }

  public static class Builder {

    private final String erpk;
    private final String token;
    private final int citizenId;
    private Set<Country> includedCountries = Set.of();
    private Set<Country> excludedCountries = Set.of();
    private boolean addBlocked = false;
    private boolean addDead = false;

    Builder(String erpk, String token, Integer citizenId) {
      Objects.requireNonNull(erpk, "erpk cannot be null");
      Objects.requireNonNull(token, "token cannot be null");
      Objects.requireNonNull(citizenId, "citizenId cannot be null");
      if (citizenId < 178) {
        throw new IllegalArgumentException("citizenId cannot be less than 178");
      }
      this.erpk = erpk;
      this.token = token;
      this.citizenId = citizenId;
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

    public AddFriendRequestConfig build() {
      return new AddFriendRequestConfig(this);
    }
  }
}
