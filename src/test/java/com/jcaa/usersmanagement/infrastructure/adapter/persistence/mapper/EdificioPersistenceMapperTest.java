package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.application.service.mapper.EdificioApplicationMapper;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.EdificioPersistenceDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EdificioPersistenceMapperTest {

  @Mock private ResultSet resultSet;

  @Test
  void shouldMapModelToDtoAndResultSetToModelList() throws SQLException {
    final EdificioModel model =
        EdificioApplicationMapper.fromCreateCommandToModel(
            new CreateEdificioCommand(
                "edf-001",
                "Torre Del Mar",
                3600.0,
                48.0,
                16,
                32,
                2,
                "Parqueadero Mar",
                1,
                "Colombia",
                "Bolívar",
                "Cartagena",
                true,
                500000.0,
                true));

    final EdificioPersistenceDto dto = EdificioPersistenceMapper.fromModelToDto(model);
    assertEquals("edf-001", dto.id());
    assertEquals("Torre Del Mar", dto.nombre());
    assertTrue(dto.tieneAscensor());

    when(resultSet.next()).thenReturn(true, false);
    when(resultSet.getString("id")).thenReturn("edf-001");
    when(resultSet.getString("nombre")).thenReturn("Torre Del Mar");
    when(resultSet.getDouble("metros_cuadrados")).thenReturn(3600.0);
    when(resultSet.getDouble("altura")).thenReturn(48.0);
    when(resultSet.getInt("num_pisos")).thenReturn(16);
    when(resultSet.getInt("num_apartamentos")).thenReturn(32);
    when(resultSet.getInt("num_oficinas")).thenReturn(2);
    when(resultSet.getString("nombre_parqueadero")).thenReturn("Parqueadero Mar");
    when(resultSet.getInt("num_piscinas")).thenReturn(1);
    when(resultSet.getString("pais")).thenReturn("Colombia");
    when(resultSet.getString("departamento")).thenReturn("Bolívar");
    when(resultSet.getString("ciudad")).thenReturn("Cartagena");
    when(resultSet.getBoolean("tiene_ascensor")).thenReturn(true);
    when(resultSet.getDouble("valor_administracion")).thenReturn(500000.0);
    when(resultSet.getBoolean("tiene_zona_social")).thenReturn(true);
    when(resultSet.getString("created_at")).thenReturn("2026-01-01 00:00:00");
    when(resultSet.getString("updated_at")).thenReturn("2026-01-01 00:00:00");

    final List<EdificioModel> list = EdificioPersistenceMapper.fromResultSetToModelList(resultSet);
    assertEquals(1, list.size());
    assertEquals("edf-001", list.get(0).getId().value());
  }
}
