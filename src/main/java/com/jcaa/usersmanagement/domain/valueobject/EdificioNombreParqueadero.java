package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidEdificioFieldException;
import java.util.Objects;

public record EdificioNombreParqueadero(String value) {

  private static final String FIELD = "nombreParqueadero";
  private static final int MIN_LENGTH = 3;
  private static final int MAX_LENGTH = 100;

  public EdificioNombreParqueadero {
    final String normalizedValue =
        Objects.requireNonNull(value, "EdificioNombreParqueadero cannot be null").trim();
    if (normalizedValue.isEmpty()) {
      throw InvalidEdificioFieldException.becauseValueIsEmpty(FIELD);
    }
    if (normalizedValue.length() < MIN_LENGTH) {
      throw InvalidEdificioFieldException.becauseLengthIsTooShort(FIELD, MIN_LENGTH);
    }
    if (normalizedValue.length() > MAX_LENGTH) {
      throw InvalidEdificioFieldException.becauseLengthIsTooLong(FIELD, MAX_LENGTH);
    }
    value = normalizedValue;
  }

  @Override
  public String toString() {
    return value;
  }
}
