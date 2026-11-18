package com.jcaa.usersmanagement.domain.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EdificioExceptionsTest {

  @Test
  void shouldBuildDomainExceptionsWithDescriptiveMessages() {
    assertThat(InvalidEdificioIdException.becauseValueIsEmpty().getMessage())
        .contains("identificador del edificio");
    assertThat(InvalidEdificioIdException.becauseLengthIsTooLong(36).getMessage())
        .contains("36");
    assertThat(InvalidEdificioFieldException.becauseValueIsEmpty("nombre").getMessage())
        .contains("nombre");
    assertThat(InvalidEdificioFieldException.becauseLengthIsTooShort("nombre", 3).getMessage())
        .contains("3");
    assertThat(InvalidEdificioFieldException.becauseLengthIsTooLong("nombre", 120).getMessage())
        .contains("120");
    assertThat(InvalidEdificioFieldException.becauseValueMustBePositive("altura").getMessage())
        .contains("mayor que cero");
    assertThat(
            InvalidEdificioFieldException.becauseValueMustNotBeNegative("numPiscinas").getMessage())
        .contains("negativo");
    assertThat(
            InvalidEdificioFieldException.becauseValueExceedsMaximum("numPisos", 300).getMessage())
        .contains("300");
    assertThat(InvalidEdificioFieldException.becauseBuildingHasNoUnits().getMessage())
        .contains("apartamento o una oficina");
    assertThat(
            InvalidEdificioFieldException.becauseHeightIsInconsistentWithFloors(2.0).getMessage())
        .contains("2.0");
    assertThat(
            EdificioAlreadyExistsException.becauseNameAlreadyExistsInCity(
                    "Torre Del Mar", "Cartagena")
                .getMessage())
        .contains("Torre Del Mar")
        .contains("Cartagena");
    assertThat(EdificioNotFoundException.becauseIdWasNotFound("edf-001").getMessage())
        .contains("edf-001");
  }
}
