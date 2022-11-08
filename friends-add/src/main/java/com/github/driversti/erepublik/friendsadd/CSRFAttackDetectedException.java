package com.github.driversti.erepublik.friendsadd;

public class CSRFAttackDetectedException extends RuntimeException {

  public CSRFAttackDetectedException(String message) {
    super(message);
  }
}
