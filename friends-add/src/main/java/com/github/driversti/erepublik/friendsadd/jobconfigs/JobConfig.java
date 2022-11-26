package com.github.driversti.erepublik.friendsadd.jobconfigs;

import static java.lang.String.format;

import java.util.Objects;
import java.util.Set;

public class JobConfig {

  private final String erpk;
  private final String token;
  private int fromId;

  private int toId;
  private Set<Country> includedCountries;
  private Set<Country> excludedCountries;
  private boolean addBlocked;
  private boolean addDead;
  private boolean addBanned;

  private JobConfig(Builder builder) {
    this.erpk = builder.erpk;
    this.token = builder.token;
    if (builder.fromId > builder.toId) {
      throw new IllegalArgumentException(
          format("'fromId' (%d) cannot be bigger than 'toId' (%d)", builder.fromId, builder.toId));
    }
    this.fromId = builder.fromId;
    this.toId = builder.toId;
    this.includedCountries = builder.includedCountries;
    this.excludedCountries = builder.excludedCountries;
    this.addBlocked = builder.addBlocked;
    this.addDead = builder.addDead;
    this.addBanned = builder.addBanned;
  }

  public String erpk() {
    return erpk;
  }

  public String token() {
    return token;
  }

  public int fromId() {
    return fromId;
  }

  public int toId() {
    return toId;
  }

  public Set<Country> includedCountries() {
    return includedCountries;
  }

  public Set<Country> excludedCountries() {
    return excludedCountries;
  }

  public boolean addBlocked() {
    return addBlocked;
  }

  public boolean addDead() {
    return addDead;
  }

  public boolean addBanned() {
    return addBanned;
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
    if (!(o instanceof JobConfig that)) {
      return false;
    }
    return fromId == that.fromId && toId == that.toId && addBlocked == that.addBlocked
        && addDead == that.addDead && addBanned == that.addBanned &&
        erpk.equals(that.erpk) && token.equals(that.token) &&
        Objects.equals(includedCountries, that.includedCountries) &&
        Objects.equals(excludedCountries, that.excludedCountries);
  }

  @Override
  public String toString() {
    return "JobConfig{" +
        "erpk='" + erpk + '\'' +
        ", token='" + token + '\'' +
        ", fromId=" + fromId +
        ", toId=" + toId +
        ", includedCountries=" + includedCountries +
        ", excludedCountries=" + excludedCountries +
        ", addBlocked=" + addBlocked +
        ", addDead=" + addDead +
        ", addBanned=" + addBanned +
        '}';
  }

  public static class Builder {

    private final String erpk;
    private final String token;
    private int fromId = 178;
    // todo load the latest ID from https://erepublik.tools/en/society/citizen-registration/0/2022-10-16/2022-10-17/1
    private int toId = 9671074; // on day 2022-10-17
    private Set<Country> includedCountries = Set.of();
    private Set<Country> excludedCountries = Set.of();
    private boolean addBlocked;
    private boolean addDead;
    private boolean addBanned;

    public Builder(String erpk, String token) {
      Objects.requireNonNull(erpk, "'erpk' cannot be null");
      Objects.requireNonNull(token, "'token' cannot be null");
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

    public Builder addBanned(boolean addBanned) {
      this.addBanned = addBanned;
      return this;
    }

    public JobConfig build() {
      return new JobConfig(this);
    }
  }
}
