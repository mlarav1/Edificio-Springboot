package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetEdificioByIdQuery;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateEdificioRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateEdificioRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.EdificioRestResponse;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EdificioRestMapper {

  public CreateEdificioCommand toCreateCommand(final CreateEdificioRestRequest request) {
    return new CreateEdificioCommand(
        request.id(),
        request.nombre(),
        request.metrosCuadrados(),
        request.altura(),
        request.numPisos(),
        request.numApartamentos(),
        request.numOficinas(),
        request.nombreParqueadero(),
        request.numPiscinas(),
        request.pais(),
        request.departamento(),
        request.ciudad(),
        request.tieneAscensor(),
        request.valorAdministracion(),
        request.tieneZonaSocial());
  }

  public UpdateEdificioCommand toUpdateCommand(
      final String id, final UpdateEdificioRestRequest request) {
    return new UpdateEdificioCommand(
        id,
        request.nombre(),
        request.metrosCuadrados(),
        request.altura(),
        request.numPisos(),
        request.numApartamentos(),
        request.numOficinas(),
        request.nombreParqueadero(),
        request.numPiscinas(),
        request.pais(),
        request.departamento(),
        request.ciudad(),
        request.tieneAscensor(),
        request.valorAdministracion(),
        request.tieneZonaSocial());
  }

  public GetEdificioByIdQuery toGetByIdQuery(final String id) {
    return new GetEdificioByIdQuery(id);
  }

  public DeleteEdificioCommand toDeleteCommand(final String id) {
    return new DeleteEdificioCommand(id);
  }

  public EdificioRestResponse toResponse(final EdificioModel edificio) {
    return new EdificioRestResponse(
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
        edificio.totalUnidades(),
        edificio.alturaPorPiso());
  }

  public List<EdificioRestResponse> toResponseList(final List<EdificioModel> edificios) {
    return edificios.stream().map(EdificioRestMapper::toResponse).toList();
  }
}
