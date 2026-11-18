package com.jcaa.usersmanagement.domain.event;

import static org.assertj.core.api.Assertions.assertThat;

import com.jcaa.usersmanagement.domain.model.EdificioModel;
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

class EdificioDomainEventsTest {

  @Test
  void shouldExposeExpectedEventNamesAndPayloads() {
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

    final EdificioCreatedDomainEvent created = new EdificioCreatedDomainEvent(edificio);
    final EdificioUpdatedDomainEvent updated = new EdificioUpdatedDomainEvent(edificio);
    final EdificioDeletedDomainEvent deleted =
        new EdificioDeletedDomainEvent(new EdificioId("edf-001"));

    assertThat(created.getEventName()).isEqualTo("edificio.created");
    assertThat(created.payload()).containsEntry("id", "edf-001");
    assertThat(updated.getEventName()).isEqualTo("edificio.updated");
    assertThat(updated.payload()).containsEntry("nombre", "Torre Del Puerto");
    assertThat(deleted.getEventName()).isEqualTo("edificio.deleted");
    assertThat(deleted.payload()).containsEntry("id", "edf-001");
  }
}
