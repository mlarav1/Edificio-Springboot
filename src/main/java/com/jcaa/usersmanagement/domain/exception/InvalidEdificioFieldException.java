package com.jcaa.usersmanagement.domain.exception;

import java.util.Locale;

public final class InvalidEdificioFieldException extends DomainException {

  private InvalidEdificioFieldException(final String message) {
    super(message);
  }

  public static InvalidEdificioFieldException becauseValueIsEmpty(final String field) {
    return new InvalidEdificioFieldException(
        String.format("El campo \"%s\" del edificio no puede estar vacío.", field));
  }

  public static InvalidEdificioFieldException becauseLengthIsTooShort(
      final String field, final int minLength) {
    return new InvalidEdificioFieldException(
        String.format(
            "El campo \"%s\" del edificio debe tener al menos %d caracteres.", field, minLength));
  }

  public static InvalidEdificioFieldException becauseLengthIsTooLong(
      final String field, final int maxLength) {
    return new InvalidEdificioFieldException(
        String.format(
            "El campo \"%s\" del edificio no puede superar %d caracteres.", field, maxLength));
  }

  public static InvalidEdificioFieldException becauseValueMustBePositive(final String field) {
    return new InvalidEdificioFieldException(
        String.format("El campo \"%s\" del edificio debe ser mayor que cero.", field));
  }

  public static InvalidEdificioFieldException becauseValueMustNotBeNegative(final String field) {
    return new InvalidEdificioFieldException(
        String.format("El campo \"%s\" del edificio no puede ser negativo.", field));
  }

  public static InvalidEdificioFieldException becauseValueExceedsMaximum(
      final String field, final Number maximum) {
    return new InvalidEdificioFieldException(
        String.format("El campo \"%s\" del edificio no puede ser mayor que %s.", field, maximum));
  }

  public static InvalidEdificioFieldException becauseBuildingHasNoUnits() {
    return new InvalidEdificioFieldException(
        "El edificio debe tener al menos un apartamento o una oficina.");
  }

  public static InvalidEdificioFieldException becauseHeightIsInconsistentWithFloors(
      final double minimumHeightPerFloor) {
    return new InvalidEdificioFieldException(
        String.format(
            Locale.ROOT,
            "La altura del edificio es inconsistente con su número de pisos (mínimo %.1f m por piso).",
            minimumHeightPerFloor));
  }
}
