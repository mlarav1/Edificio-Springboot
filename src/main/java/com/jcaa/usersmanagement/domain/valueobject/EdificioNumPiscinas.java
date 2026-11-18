package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidEdificioFieldException;

public record EdificioNumPiscinas(int value) {

  private static final String FIELD = "numPiscinas";
  private static final int MAX_VALUE = 50;

  public EdificioNumPiscinas {
    if (value < 0) {
      throw InvalidEdificioFieldException.becauseValueMustNotBeNegative(FIELD);
    }
    if (value > MAX_VALUE) {
      throw InvalidEdificioFieldException.becauseValueExceedsMaximum(FIELD, MAX_VALUE);
    }
  }

  @Override
  public String toString() {
    return Integer.toString(value);
  }
}
