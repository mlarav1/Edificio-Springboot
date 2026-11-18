package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidEdificioFieldException;
import java.util.Locale;

public record EdificioValorAdministracion(double value) {

  private static final String FIELD = "valorAdministracion";
  private static final double MAX_VALUE = 9_999_999_999.99;

  public EdificioValorAdministracion {
    if (value < 0) {
      throw InvalidEdificioFieldException.becauseValueMustNotBeNegative(FIELD);
    }
    if (value > MAX_VALUE) {
      throw InvalidEdificioFieldException.becauseValueExceedsMaximum(FIELD, MAX_VALUE);
    }
    value = Math.round(value * 100.0) / 100.0;
  }

  @Override
  public String toString() {
    return String.format(Locale.ROOT, "%.2f", value);
  }
}
