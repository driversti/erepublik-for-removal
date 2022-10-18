package com.github.driversti.erepublik.friendsadd;

public enum ArgumentKey {

  TOKEN("token"),
  ERPK("erpk"),
  FROM_ID("from_id"),
  TO_ID("to_id"),
  INCLUDE_COUNTRIES("include_countries"),
  EXCLUDE_COUNTRIES("exclude_countries"),
  ADD_BLOCKED("add_blocked"),
  ADD_DEAD("add_dead");

  private final String value;

  ArgumentKey(String value) {
    this.value = value;
  }

  static ArgumentKey fromValue(String value) {
    return switch (value) {
      case "token" -> TOKEN;
      case "erpk" -> ERPK;
      case "from_id" -> FROM_ID;
      case "to_id" -> TO_ID;
      case "include_countries" -> INCLUDE_COUNTRIES;
      case "exclude_countries" -> EXCLUDE_COUNTRIES;
      case "add_blocked" -> ADD_BLOCKED;
      case "add_dead" -> ADD_DEAD;
      default -> throw new IllegalArgumentException("Not expected argument key: " + value);
    };
  }
}
