package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.out.GetEdificioByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetEdificioByNombreAndCiudadPort;
import com.jcaa.usersmanagement.application.port.out.UpdateEdificioPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEdificioCommand;
import com.jcaa.usersmanagement.application.service.mapper.EdificioApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EdificioAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.EdificioNotFoundException;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateEdificioServiceTest {

  @Mock private UpdateEdificioPort updateEdificioPort;
  @Mock private GetEdificioByIdPort getEdificioByIdPort;
  @Mock private GetEdificioByNombreAndCiudadPort getEdificioByNombreAndCiudadPort;

  private UpdateEdificioService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      service =
          new UpdateEdificioService(
              updateEdificioPort,
              getEdificioByIdPort,
              getEdificioByNombreAndCiudadPort,
              factory.getValidator());
    }
  }

  @Test
  void shouldUpdateEdificioWhenExistsAndNameIsAvailable() {
    final UpdateEdificioCommand command = sampleCommand("edf-001");
    final EdificioModel model = EdificioApplicationMapper.fromUpdateCommandToModel(command);

    when(getEdificioByIdPort.getById(any())).thenReturn(Optional.of(model));
    when(getEdificioByNombreAndCiudadPort.getByNombreAndCiudad(any(), any()))
        .thenReturn(Optional.of(model));
    when(updateEdificioPort.update(any())).thenReturn(model);

    final EdificioModel result = service.execute(command);

    assertEquals("edf-001", result.getId().value());
    verify(updateEdificioPort).update(any(EdificioModel.class));
  }

  @Test
  void shouldThrowWhenEdificioDoesNotExist() {
    final UpdateEdificioCommand command = sampleCommand("edf-404");

    when(getEdificioByIdPort.getById(any())).thenReturn(Optional.empty());

    assertThrows(EdificioNotFoundException.class, () -> service.execute(command));
    verify(updateEdificioPort, never()).update(any());
  }

  @Test
  void shouldThrowWhenAnotherEdificioHasSameNameInCity() {
    final UpdateEdificioCommand command = sampleCommand("edf-001");
    final EdificioModel current = EdificioApplicationMapper.fromUpdateCommandToModel(command);
    final EdificioModel other =
        EdificioApplicationMapper.fromUpdateCommandToModel(sampleCommand("edf-999"));

    when(getEdificioByIdPort.getById(any())).thenReturn(Optional.of(current));
    when(getEdificioByNombreAndCiudadPort.getByNombreAndCiudad(any(), any()))
        .thenReturn(Optional.of(other));

    assertThrows(EdificioAlreadyExistsException.class, () -> service.execute(command));
    verify(updateEdificioPort, never()).update(any());
  }

  private static UpdateEdificioCommand sampleCommand(final String id) {
    return new UpdateEdificioCommand(
        id,
        "Torre Bocagrande",
        4200.0,
        60.0,
        20,
        40,
        8,
        "Parqueadero Principal",
        2,
        "Colombia",
        "Bolívar",
        "Cartagena",
        true,
        550000.0,
        true);
  }
}
