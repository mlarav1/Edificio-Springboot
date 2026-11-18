package com.jcaa.usersmanagement.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jcaa.usersmanagement.domain.exception.InvalidEdificioFieldException;
import com.jcaa.usersmanagement.domain.valueobject.EdificioAltura;
import com.jcaa.usersmanagement.domain.valueobject.EdificioId;
import com.jcaa.usersmanagement.domain.valueobject.EdificioMetrosCuadrados;
import com.jcaa.usersmanagement.domain.valueobject.EdificioNombre;
import com.jcaa.usersmanagement.domain.valueobject.EdificioNombreParqueadero;
import com.jcaa.usersmanagement.domain.valueobject.EdificioNumApartamentos;
import com.jcaa.usersmanagement.domain.valueobject.EdificioNumOficinas;
import com.jcaa.usersmanagement.domain.valueobject.EdificioNumPiscinas;
import com.jcaa.usersmanagement.domain.valueobject.EdificioNumPisos;
import com.jcaa.usersmanagement.domain.valueobject.EdificioUbicacion;
import com.jcaa.usersmanagement.domain.valueobject.EdificioValorAdministracion;
import org.junit.jupiter.api.Test;

class EdificioModelTest {

  @Test
  void shouldCreateEdificioModelWhenBusinessRulesAreSatisfied() {
    final EdificioModel edificio =
        EdificioModel.create(
            new EdificioId("edf-001"),
            new EdificioNombre("Torre Del Puerto"),
            new EdificioMetrosCuadrados(3500.0),
            new EdificioAltura(45.0),
            new EdificioNumPisos(15),
            new EdificioNumApartamentos(30),
            new EdificioNumOficinas(5),
            new EdificioNombreParqueadero("Parqueadero Central"),
            new EdificioNumPiscinas(1),
            new EdificioUbicacion("Colombia", "Bolívar", "Cartagena"),
            true,
            new EdificioValorAdministracion(520000.0),
            true);

    assertThat(edificio.totalUnidades()).isEqualTo(35);
    assertThat(edificio.alturaPorPiso()).isEqualTo(3.0);
    assertThat(edificio.isTieneAscensor()).isTrue();
    assertThat(edificio.isTieneZonaSocial()).isTrue();
  }

  @Test
  void shouldFailWhenBuildingHasNeitherApartmentsNorOffices() {
    assertThatThrownBy(
            () ->
                EdificioModel.create(
                    new EdificioId("edf-002"),
                    new EdificioNombre("Torre Vacia"),
                    new EdificioMetrosCuadrados(1200.0),
                    new EdificioAltura(20.0),
                    new EdificioNumPisos(5),
                    new EdificioNumApartamentos(0),
                    new EdificioNumOficinas(0),
                    new EdificioNombreParqueadero("Parqueadero Sur"),
                    new EdificioNumPiscinas(0),
                    new EdificioUbicacion("Colombia", "Bolívar", "Cartagena"),
                    false,
                    new EdificioValorAdministracion(100000.0),
                    false))
        .isInstanceOf(InvalidEdificioFieldException.class)
        .hasMessageContaining("apartamento o una oficina");
  }

  @Test
  void shouldFailWhenHeightPerFloorIsLessThanTwoMeters() {
    assertThatThrownBy(
            () ->
                EdificioModel.create(
                    new EdificioId("edf-003"),
                    new EdificioNombre("Torre Baja"),
                    new EdificioMetrosCuadrados(1200.0),
                    new EdificioAltura(10.0),
                    new EdificioNumPisos(10),
                    new EdificioNumApartamentos(10),
                    new EdificioNumOficinas(0),
                    new EdificioNombreParqueadero("Parqueadero Norte"),
                    new EdificioNumPiscinas(0),
                    new EdificioUbicacion("Colombia", "Bolívar", "Cartagena"),
                    true,
                    new EdificioValorAdministracion(150000.0),
                    false))
        .isInstanceOf(InvalidEdificioFieldException.class)
        .hasMessageContaining("inconsistente");
  }
}
