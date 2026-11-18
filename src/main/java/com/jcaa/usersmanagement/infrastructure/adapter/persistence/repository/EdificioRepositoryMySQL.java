package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeleteEdificioPort;
import com.jcaa.usersmanagement.application.port.out.GetAllEdificiosPort;
import com.jcaa.usersmanagement.application.port.out.GetEdificioByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetEdificioByNombreAndCiudadPort;
import com.jcaa.usersmanagement.application.port.out.SaveEdificioPort;
import com.jcaa.usersmanagement.application.port.out.UpdateEdificioPort;
import com.jcaa.usersmanagement.domain.exception.EdificioNotFoundException;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import com.jcaa.usersmanagement.domain.valueobject.EdificioId;
import com.jcaa.usersmanagement.domain.valueobject.EdificioNombre;
import com.jcaa.usersmanagement.domain.valueobject.EdificioUbicacion;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.EdificioPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.EdificioPersistenceMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EdificioRepositoryMySQL
    implements SaveEdificioPort,
        UpdateEdificioPort,
        GetEdificioByIdPort,
        GetEdificioByNombreAndCiudadPort,
        GetAllEdificiosPort,
        DeleteEdificioPort {

  private static final String COLUMNS =
      "id, nombre, metros_cuadrados, altura, num_pisos, num_apartamentos, num_oficinas, "
          + "nombre_parqueadero, num_piscinas, pais, departamento, ciudad, "
          + "tiene_ascensor, valor_administracion, tiene_zona_social, created_at, updated_at";

  private static final String SQL_INSERT =
      "INSERT INTO edificios ("
          + COLUMNS
          + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

  private static final String SQL_UPDATE =
      "UPDATE edificios SET "
          + "nombre = ?, metros_cuadrados = ?, altura = ?, num_pisos = ?, "
          + "num_apartamentos = ?, num_oficinas = ?, nombre_parqueadero = ?, num_piscinas = ?, "
          + "pais = ?, departamento = ?, ciudad = ?, tiene_ascensor = ?, "
          + "valor_administracion = ?, tiene_zona_social = ?, updated_at = NOW() "
          + "WHERE id = ?";

  private static final String SQL_SELECT_BY_ID =
      "SELECT " + COLUMNS + " FROM edificios WHERE id = ? LIMIT 1";

  private static final String SQL_SELECT_BY_NOMBRE_AND_CIUDAD =
      "SELECT "
          + COLUMNS
          + " FROM edificios WHERE LOWER(nombre) = LOWER(?) AND LOWER(ciudad) = LOWER(?) LIMIT 1";

  private static final String SQL_SELECT_ALL =
      "SELECT " + COLUMNS + " FROM edificios ORDER BY nombre ASC";

  private static final String SQL_DELETE = "DELETE FROM edificios WHERE id = ?";

  private final DataSource dataSource;

  @Override
  public EdificioModel save(final EdificioModel edificio) {
    final EdificioPersistenceDto dto = EdificioPersistenceMapper.fromModelToDto(edificio);
    executeSave(dto);
    return findByIdOrFail(edificio.getId());
  }

  @Override
  public EdificioModel update(final EdificioModel edificio) {
    final EdificioPersistenceDto dto = EdificioPersistenceMapper.fromModelToDto(edificio);
    executeUpdate(dto);
    return findByIdOrFail(edificio.getId());
  }

  @Override
  public Optional<EdificioModel> getById(final EdificioId id) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
      statement.setString(1, id.value());
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(EdificioPersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindByIdFailed(id.value(), exception);
    }
  }

  @Override
  public Optional<EdificioModel> getByNombreAndCiudad(
      final EdificioNombre nombre, final EdificioUbicacion ubicacion) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement =
            connection.prepareStatement(SQL_SELECT_BY_NOMBRE_AND_CIUDAD)) {
      statement.setString(1, nombre.value());
      statement.setString(2, ubicacion.ciudad());
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(EdificioPersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindByIdFailed(
          nombre.value() + "@" + ubicacion.ciudad(), exception);
    }
  }

  @Override
  public List<EdificioModel> getAll() {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
      final ResultSet resultSet = statement.executeQuery();
      return EdificioPersistenceMapper.fromResultSetToModelList(resultSet);
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindAllFailed(exception);
    }
  }

  @Override
  public void delete(final EdificioId id) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
      statement.setString(1, id.value());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseDeleteFailed(id.value(), exception);
    }
  }

  private void executeSave(final EdificioPersistenceDto dto) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
      statement.setString(1, dto.id());
      statement.setString(2, dto.nombre());
      statement.setDouble(3, dto.metrosCuadrados());
      statement.setDouble(4, dto.altura());
      statement.setInt(5, dto.numPisos());
      statement.setInt(6, dto.numApartamentos());
      statement.setInt(7, dto.numOficinas());
      statement.setString(8, dto.nombreParqueadero());
      statement.setInt(9, dto.numPiscinas());
      statement.setString(10, dto.pais());
      statement.setString(11, dto.departamento());
      statement.setString(12, dto.ciudad());
      statement.setBoolean(13, dto.tieneAscensor());
      statement.setDouble(14, dto.valorAdministracion());
      statement.setBoolean(15, dto.tieneZonaSocial());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseSaveFailed(dto.id(), exception);
    }
  }

  private void executeUpdate(final EdificioPersistenceDto dto) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
      statement.setString(1, dto.nombre());
      statement.setDouble(2, dto.metrosCuadrados());
      statement.setDouble(3, dto.altura());
      statement.setInt(4, dto.numPisos());
      statement.setInt(5, dto.numApartamentos());
      statement.setInt(6, dto.numOficinas());
      statement.setString(7, dto.nombreParqueadero());
      statement.setInt(8, dto.numPiscinas());
      statement.setString(9, dto.pais());
      statement.setString(10, dto.departamento());
      statement.setString(11, dto.ciudad());
      statement.setBoolean(12, dto.tieneAscensor());
      statement.setDouble(13, dto.valorAdministracion());
      statement.setBoolean(14, dto.tieneZonaSocial());
      statement.setString(15, dto.id());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseUpdateFailed(dto.id(), exception);
    }
  }

  private EdificioModel findByIdOrFail(final EdificioId id) {
    return getById(id)
        .orElseThrow(() -> EdificioNotFoundException.becauseIdWasNotFound(id.value()));
  }
}
