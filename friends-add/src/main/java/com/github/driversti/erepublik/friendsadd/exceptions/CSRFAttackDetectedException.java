package com.github.driversti.erepublik.friendsadd.exceptions;

public class CSRFAttackDetectedException extends RuntimeException {

  public CSRFAttackDetectedException(String message) {
    super(message);
  }
}
