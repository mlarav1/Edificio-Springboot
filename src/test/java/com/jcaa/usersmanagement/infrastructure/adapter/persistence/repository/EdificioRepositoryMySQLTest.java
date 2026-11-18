package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.application.service.mapper.EdificioApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EdificioNotFoundException;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import com.jcaa.usersmanagement.domain.valueobject.EdificioId;
import com.jcaa.usersmanagement.domain.valueobject.EdificioNombre;
import com.jcaa.usersmanagement.domain.valueobject.EdificioUbicacion;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EdificioRepositoryMySQLTest {

  @Mock private DataSource dataSource;
  @Mock private Connection connection;
  @Mock private PreparedStatement statement;
  @Mock private ResultSet resultSet;

  private EdificioRepositoryMySQL repository;
  private EdificioModel edificioModel;

  @BeforeEach
  void setUp() {
    repository = new EdificioRepositoryMySQL(dataSource);
    edificioModel =
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
  }

  private void configureStatementAndResultSet() throws SQLException {
    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
  }

  private void configureResultSetRow() throws SQLException {
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
  }

  @Test
  void shouldSaveAndReturnEdificio() throws SQLException {
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(true);
    configureResultSetRow();

    final EdificioModel saved = repository.save(edificioModel);
    assertEquals("edf-001", saved.getId().value());
  }

  @Test
  void shouldThrowEdificioNotFoundWhenSaveSelectReturnsEmpty() throws SQLException {
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(false);

    assertThrows(EdificioNotFoundException.class, () -> repository.save(edificioModel));
  }

  @Test
  void shouldUpdateAndReturnEdificio() throws SQLException {
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(true);
    configureResultSetRow();

    final EdificioModel updated = repository.update(edificioModel);
    assertEquals("Torre Del Mar", updated.getNombre().value());
  }

  @Test
  void shouldFindByNombreAndCiudad() throws SQLException {
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(true);
    configureResultSetRow();

    final Optional<EdificioModel> found =
        repository.getByNombreAndCiudad(
            new EdificioNombre("Torre Del Mar"),
            new EdificioUbicacion("Colombia", "Bolívar", "Cartagena"));

    assertTrue(found.isPresent());
  }

  @Test
  void shouldGetAllAndDeleteEdificios() throws SQLException {
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(true, false);
    configureResultSetRow();

    final List<EdificioModel> all = repository.getAll();
    assertEquals(1, all.size());

    assertDoesNotThrow(() -> repository.delete(new EdificioId("edf-001")));
  }

  @Test
  void shouldWrapSqlExceptionsInPersistenceException() throws SQLException {
    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

    assertThrows(PersistenceException.class, () -> repository.save(edificioModel));
    assertThrows(PersistenceException.class, () -> repository.update(edificioModel));
    assertThrows(PersistenceException.class, () -> repository.getById(new EdificioId("edf-001")));
    assertThrows(
        PersistenceException.class,
        () ->
            repository.getByNombreAndCiudad(
                new EdificioNombre("Torre Del Mar"),
                new EdificioUbicacion("Colombia", "Bolívar", "Cartagena")));
    assertThrows(PersistenceException.class, () -> repository.getAll());
    assertThrows(PersistenceException.class, () -> repository.delete(new EdificioId("edf-001")));
  }
}
