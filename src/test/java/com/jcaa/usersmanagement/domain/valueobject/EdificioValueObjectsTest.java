package com.jcaa.usersmanagement.domain.valueobject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jcaa.usersmanagement.domain.exception.InvalidEdificioFieldException;
import com.jcaa.usersmanagement.domain.exception.InvalidEdificioIdException;
import org.junit.jupiter.api.Test;

class EdificioValueObjectsTest {

  @Test
  void shouldCreateValidValueObjectsAndNormalizeValues() {
    final EdificioId id = new EdificioId("  edf-001  ");
    final EdificioNombre nombre = new EdificioNombre("  Torre Empresarial Caribe  ");
    final EdificioMetrosCuadrados metros = new EdificioMetrosCuadrados(4520.556);
    final EdificioAltura altura = new EdificioAltura(65.432);
    final EdificioNumPisos pisos = new EdificioNumPisos(20);
    final EdificioNumApartamentos aptos = new EdificioNumApartamentos(40);
    final EdificioNumOficinas oficinas = new EdificioNumOficinas(10);
    final EdificioNombreParqueadero parqueadero = new EdificioNombreParqueadero("  Sótano Norte ");
    final EdificioNumPiscinas piscinas = new EdificioNumPiscinas(2);
    final EdificioUbicacion ubicacion =
        new EdificioUbicacion(" Colombia ", " Bolívar ", " Cartagena ");
    final EdificioValorAdministracion admin = new EdificioValorAdministracion(480000.555);

    assertThat(id.value()).isEqualTo("edf-001");
    assertThat(nombre.value()).isEqualTo("Torre Empresarial Caribe");
    assertThat(metros.value()).isEqualTo(4520.56);
    assertThat(altura.value()).isEqualTo(65.43);
    assertThat(pisos.value()).isEqualTo(20);
    assertThat(aptos.value()).isEqualTo(40);
    assertThat(oficinas.value()).isEqualTo(10);
    assertThat(parqueadero.value()).isEqualTo("Sótano Norte");
    assertThat(piscinas.value()).isEqualTo(2);
    assertThat(ubicacion.pais()).isEqualTo("Colombia");
    assertThat(ubicacion.departamento()).isEqualTo("Bolívar");
    assertThat(ubicacion.ciudad()).isEqualTo("Cartagena");
    assertThat(ubicacion.toString()).isEqualTo("Cartagena, Bolívar, Colombia");
    assertThat(admin.value()).isEqualTo(480000.56);
  }

  @Test
  void shouldRejectInvalidValueObjectInputs() {
    assertThatThrownBy(() -> new EdificioId("   "))
        .isInstanceOf(InvalidEdificioIdException.class);
    assertThatThrownBy(() -> new EdificioId("a".repeat(37)))
        .isInstanceOf(InvalidEdificioIdException.class);
    assertThatThrownBy(() -> new EdificioNombre("ab"))
        .isInstanceOf(InvalidEdificioFieldException.class);
    assertThatThrownBy(() -> new EdificioMetrosCuadrados(0))
        .isInstanceOf(InvalidEdificioFieldException.class);
    assertThatThrownBy(() -> new EdificioAltura(-5))
        .isInstanceOf(InvalidEdificioFieldException.class);
    assertThatThrownBy(() -> new EdificioNumPisos(0))
        .isInstanceOf(InvalidEdificioFieldException.class);
    assertThatThrownBy(() -> new EdificioNumApartamentos(-1))
        .isInstanceOf(InvalidEdificioFieldException.class);
    assertThatThrownBy(() -> new EdificioNumOficinas(-1))
        .isInstanceOf(InvalidEdificioFieldException.class);
    assertThatThrownBy(() -> new EdificioNombreParqueadero("ab"))
        .isInstanceOf(InvalidEdificioFieldException.class);
    assertThatThrownBy(() -> new EdificioNumPiscinas(-1))
        .isInstanceOf(InvalidEdificioFieldException.class);
    assertThatThrownBy(() -> new EdificioUbicacion("C", "Bolívar", "Cartagena"))
        .isInstanceOf(InvalidEdificioFieldException.class);
    assertThatThrownBy(() -> new EdificioValorAdministracion(-10))
        .isInstanceOf(InvalidEdificioFieldException.class);
  }
}
