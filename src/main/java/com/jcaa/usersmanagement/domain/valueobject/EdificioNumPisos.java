package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidEdificioFieldException;

public record EdificioNumPisos(int value) {

  private static final String FIELD = "numPisos";
  private static final int MAX_VALUE = 300;

  public EdificioNumPisos {
    if (value < 1) {
      throw InvalidEdificioFieldException.becauseValueMustBePositive(FIELD);
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
