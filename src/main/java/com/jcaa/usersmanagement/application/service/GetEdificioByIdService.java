package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetEdificioByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetEdificioByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetEdificioByIdQuery;
import com.jcaa.usersmanagement.application.service.mapper.EdificioApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EdificioNotFoundException;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import com.jcaa.usersmanagement.domain.valueobject.EdificioId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetEdificioByIdService implements GetEdificioByIdUseCase {

  private final GetEdificioByIdPort getEdificioByIdPort;
  private final Validator validator;

  @Override
  public EdificioModel execute(final GetEdificioByIdQuery query) {
    validateQuery(query);

    final EdificioId edificioId =
        EdificioApplicationMapper.fromGetEdificioByIdQueryToEdificioId(query);
    return getEdificioByIdPort
        .getById(edificioId)
        .orElseThrow(() -> EdificioNotFoundException.becauseIdWasNotFound(edificioId.value()));
  }

  private void validateQuery(final GetEdificioByIdQuery query) {
    final Set<ConstraintViolation<GetEdificioByIdQuery>> violations = validator.validate(query);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
