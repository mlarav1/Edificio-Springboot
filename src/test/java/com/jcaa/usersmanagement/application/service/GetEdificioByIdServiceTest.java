package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.out.GetEdificioByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetEdificioByIdQuery;
import com.jcaa.usersmanagement.application.service.mapper.EdificioApplicationMapper;
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
class GetEdificioByIdServiceTest {

  @Mock private GetEdificioByIdPort getEdificioByIdPort;

  private GetEdificioByIdService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      service = new GetEdificioByIdService(getEdificioByIdPort, factory.getValidator());
    }
  }

  @Test
  void shouldReturnEdificioWhenFound() {
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

    final EdificioModel result = service.execute(new GetEdificioByIdQuery("edf-001"));
    assertEquals("edf-001", result.getId().value());
  }

  @Test
  void shouldThrowWhenEdificioNotFound() {
    when(getEdificioByIdPort.getById(any())).thenReturn(Optional.empty());

    assertThrows(
        EdificioNotFoundException.class,
        () -> service.execute(new GetEdificioByIdQuery("edf-404")));
  }
}
