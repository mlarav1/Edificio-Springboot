package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidEdificioIdException;
import java.util.Objects;

public record EdificioId(String value) {

  private static final int MAX_LENGTH = 36;

  public EdificioId {
    final String normalizedValue =
        Objects.requireNonNull(value, "EdificioId cannot be null").trim();
    if (normalizedValue.isEmpty()) {
      throw InvalidEdificioIdException.becauseValueIsEmpty();
    }
    if (normalizedValue.length() > MAX_LENGTH) {
      throw InvalidEdificioIdException.becauseLengthIsTooLong(MAX_LENGTH);
    }
    value = normalizedValue;
  }

  @Override
  public String toString() {
    return value;
  }
}
