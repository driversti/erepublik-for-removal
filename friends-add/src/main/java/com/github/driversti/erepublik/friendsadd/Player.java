package com.github.driversti.erepublik.friendsadd;

class Player {

  private Citizen citizen;
  private Location location;
  private Boolean isBanned;
  private Boolean isBlocked;

  public boolean isBanned() {
    return isBanned;
  }

  void setIsBanned(Boolean banned) {
    isBanned = banned;
  }

  Player isBanned(Boolean banned) {
    isBanned = banned;
    return this;
  }

  public boolean isDead() {
    return citizen.is_alive;
  }

  Player isDead(Boolean dead) {
    createCitizenIfNeeded();
    citizen.is_alive = dead;
    return this;
  }

  void setCitizen(Citizen citizen) {
    this.citizen = citizen;
  }

  String nickname() {
    return citizen.name;
  }

  Player citizenNickname(String nickname) {
    createCitizenIfNeeded();
    citizen.name = nickname;
    return this;
  }

  void setLocation(Location location) {
    this.location = location;
  }

  boolean isBlocked() {
    return isBlocked;
  }

  Player isBlocked(Boolean blocked) {
    createCitizenIfNeeded();
    isBlocked = blocked;
    return this;
  }

  void setIsBlocked(Boolean blocked) {
    isBlocked = blocked;
  }

  private void createCitizenIfNeeded() {
    if (citizen == null) {
      citizen = new Citizen();
    }
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

    void setCitizenshipCountry(
        CitizenshipCountry citizenshipCountry) {
      this.citizenshipCountry = citizenshipCountry;
    }

    @Override
    public String toString() {
      return "Location{" +
          "citizenshipCountry=" + citizenshipCountry +
          '}';
    }

    public static class CitizenshipCountry {

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
