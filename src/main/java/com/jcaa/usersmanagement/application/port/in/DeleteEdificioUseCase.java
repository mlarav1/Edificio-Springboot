package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.DeleteEdificioCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface DeleteEdificioUseCase {
  void execute(@NotNull @Valid DeleteEdificioCommand command);
}
