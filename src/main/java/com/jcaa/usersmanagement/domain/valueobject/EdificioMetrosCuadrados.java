package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidEdificioFieldException;

public record EdificioMetrosCuadrados(double value) {

  private static final String FIELD = "metrosCuadrados";
  private static final double MAX_VALUE = 10_000_000.0;

  public EdificioMetrosCuadrados {
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
