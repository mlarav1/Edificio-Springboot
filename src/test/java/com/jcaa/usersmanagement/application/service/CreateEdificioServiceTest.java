package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.out.GetEdificioByNombreAndCiudadPort;
import com.jcaa.usersmanagement.application.port.out.SaveEdificioPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.application.service.mapper.EdificioApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EdificioAlreadyExistsException;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateEdificioServiceTest {

  @Mock private SaveEdificioPort saveEdificioPort;
  @Mock private GetEdificioByNombreAndCiudadPort getEdificioByNombreAndCiudadPort;

  private CreateEdificioService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      service =
          new CreateEdificioService(
              saveEdificioPort, getEdificioByNombreAndCiudadPort, factory.getValidator());
    }
  }

  @Test
  void shouldCreateEdificioWhenNameIsUniqueInCity() {
    final CreateEdificioCommand command = sampleCommand();
    final EdificioModel model = EdificioApplicationMapper.fromCreateCommandToModel(command);

    when(getEdificioByNombreAndCiudadPort.getByNombreAndCiudad(any(), any()))
        .thenReturn(Optional.empty());
    when(saveEdificioPort.save(any())).thenReturn(model);

    final EdificioModel saved = service.execute(command);

    assertNotNull(saved);
    assertEquals("edf-001", saved.getId().value());
    verify(saveEdificioPort).save(any(EdificioModel.class));
  }

  @Test
  void shouldThrowWhenEdificioAlreadyExistsInCity() {
    final CreateEdificioCommand command = sampleCommand();
    final EdificioModel existing = EdificioApplicationMapper.fromCreateCommandToModel(command);

    when(getEdificioByNombreAndCiudadPort.getByNombreAndCiudad(any(), any()))
        .thenReturn(Optional.of(existing));

    assertThrows(EdificioAlreadyExistsException.class, () -> service.execute(command));
    verify(saveEdificioPort, never()).save(any());
  }

  @Test
  void shouldThrowConstraintViolationWhenCommandIsInvalid() {
    final CreateEdificioCommand invalid =
        new CreateEdificioCommand(
            "", "AB", -10, 0, 0, -1, 0, "P", -1, "C", "B", "C", false, -5, false);

    assertThrows(ConstraintViolationException.class, () -> service.execute(invalid));
    verifyNoInteractions(saveEdificioPort, getEdificioByNombreAndCiudadPort);
  }

  private static CreateEdificioCommand sampleCommand() {
    return new CreateEdificioCommand(
        "edf-001",
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
