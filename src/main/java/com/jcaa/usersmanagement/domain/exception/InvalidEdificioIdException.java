package com.jcaa.usersmanagement.domain.exception;

public final class InvalidEdificioIdException extends DomainException {

  private InvalidEdificioIdException(final String message) {
    super(message);
  }

  public static InvalidEdificioIdException becauseValueIsEmpty() {
    return new InvalidEdificioIdException("El identificador del edificio no puede estar vacío.");
  }

  public static InvalidEdificioIdException becauseLengthIsTooLong(final int maxLength) {
    return new InvalidEdificioIdException(
        String.format("El identificador del edificio no puede superar %d caracteres.", maxLength));
  }
}
