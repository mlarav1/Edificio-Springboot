package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.UpdateEdificioCommand;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface UpdateEdificioUseCase {
  EdificioModel execute(@NotNull @Valid UpdateEdificioCommand command);
}
