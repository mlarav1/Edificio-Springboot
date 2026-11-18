package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

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
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.EdificioPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.EdificioEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EdificioPersistenceMapper {

  public EdificioPersistenceDto fromModelToDto(final EdificioModel edificio) {
    return new EdificioPersistenceDto(
        edificio.getId().value(),
        edificio.getNombre().value(),
        edificio.getMetrosCuadrados().value(),
        edificio.getAltura().value(),
        edificio.getNumPisos().value(),
        edificio.getNumApartamentos().value(),
        edificio.getNumOficinas().value(),
        edificio.getNombreParqueadero().value(),
        edificio.getNumPiscinas().value(),
        edificio.getUbicacion().pais(),
        edificio.getUbicacion().departamento(),
        edificio.getUbicacion().ciudad(),
        edificio.isTieneAscensor(),
        edificio.getValorAdministracion().value(),
        edificio.isTieneZonaSocial(),
        null,
        null);
  }

  public EdificioEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
    return new EdificioEntity(
        resultSet.getString("id"),
        resultSet.getString("nombre"),
        resultSet.getDouble("metros_cuadrados"),
        resultSet.getDouble("altura"),
        resultSet.getInt("num_pisos"),
        resultSet.getInt("num_apartamentos"),
        resultSet.getInt("num_oficinas"),
        resultSet.getString("nombre_parqueadero"),
        resultSet.getInt("num_piscinas"),
        resultSet.getString("pais"),
        resultSet.getString("departamento"),
        resultSet.getString("ciudad"),
        resultSet.getBoolean("tiene_ascensor"),
        resultSet.getDouble("valor_administracion"),
        resultSet.getBoolean("tiene_zona_social"),
        resultSet.getString("created_at"),
        resultSet.getString("updated_at"));
  }

  public EdificioModel fromEntityToModel(final EdificioEntity entity) {
    return EdificioModel.create(
        new EdificioId(entity.id()),
        new EdificioNombre(entity.nombre()),
        new EdificioMetrosCuadrados(entity.metrosCuadrados()),
        new EdificioAltura(entity.altura()),
        new EdificioNumPisos(entity.numPisos()),
        new EdificioNumApartamentos(entity.numApartamentos()),
        new EdificioNumOficinas(entity.numOficinas()),
        new EdificioNombreParqueadero(entity.nombreParqueadero()),
        new EdificioNumPiscinas(entity.numPiscinas()),
        new EdificioUbicacion(entity.pais(), entity.departamento(), entity.ciudad()),
        entity.tieneAscensor(),
        new EdificioValorAdministracion(entity.valorAdministracion()),
        entity.tieneZonaSocial());
  }

  public EdificioModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
    return fromEntityToModel(fromResultSetToEntity(resultSet));
  }

  public List<EdificioModel> fromResultSetToModelList(final ResultSet resultSet)
      throws SQLException {
    final List<EdificioModel> edificios = new ArrayList<>();
    while (resultSet.next()) {
      edificios.add(fromResultSetToModel(resultSet));
    }
    return edificios;
  }
}
