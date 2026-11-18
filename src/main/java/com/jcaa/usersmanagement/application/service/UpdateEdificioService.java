package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateEdificioUseCase;
import com.jcaa.usersmanagement.application.port.out.GetEdificioByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetEdificioByNombreAndCiudadPort;
import com.jcaa.usersmanagement.application.port.out.UpdateEdificioPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEdificioCommand;
import com.jcaa.usersmanagement.application.service.mapper.EdificioApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EdificioAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.EdificioNotFoundException;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateEdificioService implements UpdateEdificioUseCase {

  private final UpdateEdificioPort updateEdificioPort;
  private final GetEdificioByIdPort getEdificioByIdPort;
  private final GetEdificioByNombreAndCiudadPort getEdificioByNombreAndCiudadPort;
  private final Validator validator;

  @Override
  public EdificioModel execute(final UpdateEdificioCommand command) {
    validateCommand(command);

    final EdificioModel edificioToUpdate =
        EdificioApplicationMapper.fromUpdateCommandToModel(command);

    getEdificioByIdPort
        .getById(edificioToUpdate.getId())
        .orElseThrow(
            () ->
                EdificioNotFoundException.becauseIdWasNotFound(
                    edificioToUpdate.getId().value()));

    getEdificioByNombreAndCiudadPort
        .getByNombreAndCiudad(edificioToUpdate.getNombre(), edificioToUpdate.getUbicacion())
        .ifPresent(
            found -> {
              if (!found.getId().equals(edificioToUpdate.getId())) {
                throw EdificioAlreadyExistsException.becauseNameAlreadyExistsInCity(
                    edificioToUpdate.getNombre().value(),
                    edificioToUpdate.getUbicacion().ciudad());
              }
            });

    return updateEdificioPort.update(edificioToUpdate);
  }

  private void validateCommand(final UpdateEdificioCommand command) {
    final Set<ConstraintViolation<UpdateEdificioCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
