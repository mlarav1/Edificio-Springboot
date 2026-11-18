package com.jcaa.usersmanagement.domain.exception;

public final class EdificioAlreadyExistsException extends DomainException {

  private EdificioAlreadyExistsException(final String message) {
    super(message);
  }

  public static EdificioAlreadyExistsException becauseNameAlreadyExistsInCity(
      final String nombre, final String ciudad) {
    return new EdificioAlreadyExistsException(
        String.format("Ya existe un edificio llamado \"%s\" en la ciudad %s.", nombre, ciudad));
  }
}
