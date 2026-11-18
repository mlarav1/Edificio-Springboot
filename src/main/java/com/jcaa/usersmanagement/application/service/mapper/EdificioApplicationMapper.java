package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetEdificioByIdQuery;
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
import lombok.experimental.UtilityClass;

@UtilityClass
public class EdificioApplicationMapper {

  public EdificioModel fromCreateCommandToModel(final CreateEdificioCommand command) {
    return EdificioModel.create(
        new EdificioId(command.id()),
        new EdificioNombre(command.nombre()),
        new EdificioMetrosCuadrados(command.metrosCuadrados()),
        new EdificioAltura(command.altura()),
        new EdificioNumPisos(command.numPisos()),
        new EdificioNumApartamentos(command.numApartamentos()),
        new EdificioNumOficinas(command.numOficinas()),
        new EdificioNombreParqueadero(command.nombreParqueadero()),
        new EdificioNumPiscinas(command.numPiscinas()),
        new EdificioUbicacion(command.pais(), command.departamento(), command.ciudad()),
        command.tieneAscensor(),
        new EdificioValorAdministracion(command.valorAdministracion()),
        command.tieneZonaSocial());
  }

  public EdificioModel fromUpdateCommandToModel(final UpdateEdificioCommand command) {
    return EdificioModel.create(
        new EdificioId(command.id()),
        new EdificioNombre(command.nombre()),
        new EdificioMetrosCuadrados(command.metrosCuadrados()),
        new EdificioAltura(command.altura()),
        new EdificioNumPisos(command.numPisos()),
        new EdificioNumApartamentos(command.numApartamentos()),
        new EdificioNumOficinas(command.numOficinas()),
        new EdificioNombreParqueadero(command.nombreParqueadero()),
        new EdificioNumPiscinas(command.numPiscinas()),
        new EdificioUbicacion(command.pais(), command.departamento(), command.ciudad()),
        command.tieneAscensor(),
        new EdificioValorAdministracion(command.valorAdministracion()),
        command.tieneZonaSocial());
  }

  public EdificioId fromGetEdificioByIdQueryToEdificioId(final GetEdificioByIdQuery query) {
    return new EdificioId(query.id());
  }

  public EdificioId fromDeleteCommandToEdificioId(final DeleteEdificioCommand command) {
    return new EdificioId(command.id());
  }
}
