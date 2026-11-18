package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface CreateEdificioUseCase {
  EdificioModel execute(@NotNull @Valid CreateEdificioCommand command);
}
