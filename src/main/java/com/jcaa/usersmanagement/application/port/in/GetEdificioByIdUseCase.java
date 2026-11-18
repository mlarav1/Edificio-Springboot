package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetEdificioByIdQuery;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetEdificioByIdUseCase {
  EdificioModel execute(@NotNull @Valid GetEdificioByIdQuery query);
}
