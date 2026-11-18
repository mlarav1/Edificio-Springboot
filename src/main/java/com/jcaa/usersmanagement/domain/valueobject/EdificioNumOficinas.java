package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidEdificioFieldException;

public record EdificioNumOficinas(int value) {

  private static final String FIELD = "numOficinas";
  private static final int MAX_VALUE = 10_000;

  public EdificioNumOficinas {
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
