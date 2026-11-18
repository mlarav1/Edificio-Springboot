package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidEdificioFieldException;
import java.util.Objects;

public record EdificioUbicacion(String pais, String departamento, String ciudad) {

  private static final int MIN_LENGTH = 2;
  private static final int MAX_LENGTH = 100;

  public EdificioUbicacion {
    pais = normalize("pais", pais);
    departamento = normalize("departamento", departamento);
    ciudad = normalize("ciudad", ciudad);
  }

  private static String normalize(final String field, final String rawValue) {
    final String normalizedValue =
        Objects.requireNonNull(rawValue, field + " cannot be null").trim();
    if (normalizedValue.isEmpty()) {
      throw InvalidEdificioFieldException.becauseValueIsEmpty(field);
    }
    if (normalizedValue.length() < MIN_LENGTH) {
      throw InvalidEdificioFieldException.becauseLengthIsTooShort(field, MIN_LENGTH);
    }
    if (normalizedValue.length() > MAX_LENGTH) {
      throw InvalidEdificioFieldException.becauseLengthIsTooLong(field, MAX_LENGTH);
    }
    return normalizedValue;
  }

  @Override
  public String toString() {
    return ciudad + ", " + departamento + ", " + pais;
  }
}
