package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.jcaa.usersmanagement.application.port.out.GetAllEdificiosPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.application.service.mapper.EdificioApplicationMapper;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetAllEdificiosServiceTest {

  @Mock private GetAllEdificiosPort getAllEdificiosPort;

  @InjectMocks private GetAllEdificiosService service;

  @Test
  void shouldReturnAllEdificios() {
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

    when(getAllEdificiosPort.getAll()).thenReturn(List.of(model));

    final List<EdificioModel> result = service.execute();
    assertEquals(1, result.size());
    assertEquals("Torre Centro", result.get(0).getNombre().value());
  }
}
