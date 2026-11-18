package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidEdificioFieldException;

public record EdificioAltura(double value) {

  private static final String FIELD = "altura";
  private static final double MAX_VALUE = 1000.0;

  public EdificioAltura {
    if (value <= 0) {
      throw InvalidEdificioFieldException.becauseValueMustBePositive(FIELD);
    }
    if (value > MAX_VALUE) {
      throw InvalidEdificioFieldException.becauseValueExceedsMaximum(FIELD, MAX_VALUE);
    }
    value = Math.round(value * 100.0) / 100.0;
  }

  @Override
  public String toString() {
    return Double.toString(value);
  }
}
