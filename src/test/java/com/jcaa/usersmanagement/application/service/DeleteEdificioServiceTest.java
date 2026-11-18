package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.out.DeleteEdificioPort;
import com.jcaa.usersmanagement.application.port.out.GetEdificioByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteEdificioCommand;
import com.jcaa.usersmanagement.application.service.mapper.EdificioApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EdificioNotFoundException;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import com.jcaa.usersmanagement.domain.valueobject.EdificioId;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteEdificioServiceTest {

  @Mock private DeleteEdificioPort deleteEdificioPort;
  @Mock private GetEdificioByIdPort getEdificioByIdPort;

  private DeleteEdificioService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      service =
          new DeleteEdificioService(
              deleteEdificioPort, getEdificioByIdPort, factory.getValidator());
    }
  }

  @Test
  void shouldDeleteEdificioWhenExists() {
    final EdificioModel model =
        EdificioApplicationMapper.fromCreateCommandToModel(
            new CreateEdificioCommand(
                "edf-001",
                "Torre Centro",
                2500.0,
                30.0,
                10,
                20,
                0,
                "Parqueadero A",
                1,
                "Colombia",
                "Bolívar",
                "Cartagena",
                true,
                350000.0,
                false));

    when(getEdificioByIdPort.getById(any())).thenReturn(Optional.of(model));

    service.execute(new DeleteEdificioCommand("edf-001"));

    verify(deleteEdificioPort).delete(new EdificioId("edf-001"));
  }

  @Test
  void shouldThrowWhenEdificioToDeleteDoesNotExist() {
    when(getEdificioByIdPort.getById(any())).thenReturn(Optional.empty());

    assertThrows(
        EdificioNotFoundException.class,
        () -> service.execute(new DeleteEdificioCommand("edf-404")));
    verify(deleteEdificioPort, never()).delete(any());
  }
}
