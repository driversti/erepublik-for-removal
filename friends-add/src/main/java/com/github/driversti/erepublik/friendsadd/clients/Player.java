package com.github.driversti.erepublik.friendsadd.clients;

import com.github.driversti.erepublik.friendsadd.jobconfigs.Country;
import java.util.Collection;

public class Player {

  private Citizen citizen;
  private Location location;
  private Boolean isBanned;
  private Boolean isBlocked;

  public Player() {
    this.citizen = new Citizen();
    this.location = new Location();
  }

  public boolean isBanned() {
    return isBanned;
  }

  void setIsBanned(Boolean banned) {
    isBanned = banned;
  }

  public Player isBanned(Boolean banned) {
    isBanned = banned;
    return this;
  }

  public boolean isDead() {
    return !citizen.is_alive;
  }

  public Player isDead(Boolean isDead) {
    citizen.is_alive = !isDead;
    return this;
  }

  void setCitizen(Citizen citizen) {
    this.citizen = citizen;
  }

  public String nickname() {
    return citizen.name;
  }

  public Player citizenNickname(String nickname) {
    citizen.name = nickname;
    return this;
  }

  void setLocation(Location location) {
    this.location = location;
  }

  public boolean isBlocked() {
    return isBlocked;
  }

  public Player isBlocked(Boolean blocked) {
    isBlocked = blocked;
    return this;
  }

  void setIsBlocked(Boolean blocked) {
    isBlocked = blocked;
  }

  public Country citizenship() {
    return Country.getById(location.citizenshipCountry.id);
  }

  public Player citizenship(Country citizenship) {
    location.citizenshipCountry.id = citizenship.getId();
    location.citizenshipCountry.name = citizenship.readableName();
    return this;
  }

  @Override
  public String toString() {
    return "Player{" +
        "citizen=" + citizen +
        ", location=" + location +
        ", isBanned=" + isBanned +
        ", isBlocked=" + isBlocked +
        '}';
  }

  public boolean isCitizenOf(Collection<Integer> countryIds) {
    return countryIds.contains(location.citizenshipCountry.id);
  }

  public Integer id() {
    return citizen.id;
  }

  private static class Citizen {

    private Integer id;
    private String name;
    private Boolean is_alive;

    void setId(Integer id) {
      this.id = id;
    }

    void setName(String name) {
      this.name = name;
    }

    void setIs_alive(Boolean is_alive) {
      this.is_alive = is_alive;
    }

    @Override
    public String toString() {
      return "Citizen{" +
          "id=" + id +
          ", name='" + name + '\'' +
          ", is_alive=" + is_alive +
          '}';
    }
  }

  private static class Location {

    private CitizenshipCountry citizenshipCountry;

    Location() {
      this.citizenshipCountry = new CitizenshipCountry();
    }

    void setCitizenshipCountry(CitizenshipCountry citizenshipCountry) {
      this.citizenshipCountry = citizenshipCountry;
    }

    @Override
    public String toString() {
      return "Location{" +
          "citizenshipCountry=" + citizenshipCountry +
          '}';
    }

    private static class CitizenshipCountry {

      private Integer id;
      private String name;

      void setId(Integer id) {
        this.id = id;
      }

      void setName(String name) {
        this.name = name;
      }

      @Override
      public String toString() {
        return "CitizenshipCountry{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
      }
    }
  }
}
