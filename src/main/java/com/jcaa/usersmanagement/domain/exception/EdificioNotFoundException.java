package com.jcaa.usersmanagement.domain.exception;

public final class EdificioNotFoundException extends DomainException {

  private EdificioNotFoundException(final String message) {
    super(message);
  }

  public static EdificioNotFoundException becauseIdWasNotFound(final String id) {
    return new EdificioNotFoundException(
        String.format("No se encontró el edificio con id %s.", id));
  }
}
