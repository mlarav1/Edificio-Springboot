package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateEdificioUseCase;
import com.jcaa.usersmanagement.application.port.out.GetEdificioByNombreAndCiudadPort;
import com.jcaa.usersmanagement.application.port.out.SaveEdificioPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.application.service.mapper.EdificioApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EdificioAlreadyExistsException;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateEdificioService implements CreateEdificioUseCase {

  private final SaveEdificioPort saveEdificioPort;
  private final GetEdificioByNombreAndCiudadPort getEdificioByNombreAndCiudadPort;
  private final Validator validator;

  @Override
  public EdificioModel execute(final CreateEdificioCommand command) {
    validateCommand(command);

    final EdificioModel edificio = EdificioApplicationMapper.fromCreateCommandToModel(command);

    getEdificioByNombreAndCiudadPort
        .getByNombreAndCiudad(edificio.getNombre(), edificio.getUbicacion())
        .ifPresent(
            ignored -> {
              throw EdificioAlreadyExistsException.becauseNameAlreadyExistsInCity(
                  edificio.getNombre().value(), edificio.getUbicacion().ciudad());
            });

    return saveEdificioPort.save(edificio);
  }

  private void validateCommand(final CreateEdificioCommand command) {
    final Set<ConstraintViolation<CreateEdificioCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
