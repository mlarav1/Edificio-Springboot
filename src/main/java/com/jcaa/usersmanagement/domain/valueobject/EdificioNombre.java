package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidEdificioFieldException;
import java.util.Objects;

public record EdificioNombre(String value) {

  private static final String FIELD = "nombre";
  private static final int MIN_LENGTH = 3;
  private static final int MAX_LENGTH = 120;

  public EdificioNombre {
    final String normalizedValue =
        Objects.requireNonNull(value, "EdificioNombre cannot be null").trim();
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
