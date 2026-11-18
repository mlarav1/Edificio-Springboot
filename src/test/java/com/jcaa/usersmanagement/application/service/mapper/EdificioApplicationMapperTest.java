package com.jcaa.usersmanagement.application.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetEdificioByIdQuery;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import com.jcaa.usersmanagement.domain.valueobject.EdificioId;
import org.junit.jupiter.api.Test;

class EdificioApplicationMapperTest {

  @Test
  void shouldMapCreateAndUpdateCommandsToModel() {
    final CreateEdificioCommand createCmd =
        new CreateEdificioCommand(
            "edf-001",
            "Torre Caribe",
            3200.5,
            48.0,
            16,
            32,
            4,
            "Parqueadero Caribe",
            1,
            "Colombia",
            "Bolívar",
            "Cartagena",
            true,
            450000.0,
            true);

    final EdificioModel created = EdificioApplicationMapper.fromCreateCommandToModel(createCmd);
    assertThat(created.getId().value()).isEqualTo("edf-001");
    assertThat(created.getNombre().value()).isEqualTo("Torre Caribe");
    assertThat(created.totalUnidades()).isEqualTo(36);

    final UpdateEdificioCommand updateCmd =
        new UpdateEdificioCommand(
            "edf-001",
            "Torre Caribe Norte",
            3500.0,
            50.0,
            16,
            30,
            6,
            "Parqueadero Central",
            2,
            "Colombia",
            "Bolívar",
            "Cartagena",
            true,
            490000.0,
            false);

    final EdificioModel updated = EdificioApplicationMapper.fromUpdateCommandToModel(updateCmd);
    assertThat(updated.getNombre().value()).isEqualTo("Torre Caribe Norte");
    assertThat(updated.getNumPiscinas().value()).isEqualTo(2);

    final EdificioId fromQuery =
        EdificioApplicationMapper.fromGetEdificioByIdQueryToEdificioId(
            new GetEdificioByIdQuery("edf-001"));
    final EdificioId fromDelete =
        EdificioApplicationMapper.fromDeleteCommandToEdificioId(
            new DeleteEdificioCommand("edf-001"));

    assertThat(fromQuery.value()).isEqualTo("edf-001");
    assertThat(fromDelete.value()).isEqualTo("edf-001");
  }
}
