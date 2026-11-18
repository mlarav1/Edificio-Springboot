package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteEdificioUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteEdificioPort;
import com.jcaa.usersmanagement.application.port.out.GetEdificioByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteEdificioCommand;
import com.jcaa.usersmanagement.application.service.mapper.EdificioApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EdificioNotFoundException;
import com.jcaa.usersmanagement.domain.valueobject.EdificioId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteEdificioService implements DeleteEdificioUseCase {

  private final DeleteEdificioPort deleteEdificioPort;
  private final GetEdificioByIdPort getEdificioByIdPort;
  private final Validator validator;

  @Override
  public void execute(final DeleteEdificioCommand command) {
    validateCommand(command);

    final EdificioId edificioId = EdificioApplicationMapper.fromDeleteCommandToEdificioId(command);
    getEdificioByIdPort
        .getById(edificioId)
        .orElseThrow(() -> EdificioNotFoundException.becauseIdWasNotFound(edificioId.value()));
    deleteEdificioPort.delete(edificioId);
  }

  private void validateCommand(final DeleteEdificioCommand command) {
    final Set<ConstraintViolation<DeleteEdificioCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
