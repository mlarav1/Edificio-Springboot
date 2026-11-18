package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jcaa.usersmanagement.application.port.in.CreateEdificioUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteEdificioUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllEdificiosUseCase;
import com.jcaa.usersmanagement.application.port.in.GetEdificioByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateEdificioUseCase;
import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetEdificioByIdQuery;
import com.jcaa.usersmanagement.application.service.mapper.EdificioApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EdificioNotFoundException;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import com.jcaa.usersmanagement.infrastructure.security.JwtAuthenticationFilter;
import com.jcaa.usersmanagement.infrastructure.security.JwtTokenService;
import com.jcaa.usersmanagement.infrastructure.security.RestAccessDeniedHandler;
import com.jcaa.usersmanagement.infrastructure.security.RestAuthenticationEntryPoint;
import com.jcaa.usersmanagement.infrastructure.security.SecurityConfig;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    controllers = EdificioRestController.class,
    properties = "spring.main.web-application-type=servlet")
@Import({
  SecurityConfig.class,
  JwtAuthenticationFilter.class,
  RestAuthenticationEntryPoint.class,
  RestAccessDeniedHandler.class
})
class EdificioRestControllerTest {

  private static final String ID = "edf-001";

  @Autowired private MockMvc mockMvc;

  @MockBean private CreateEdificioUseCase createEdificioUseCase;
  @MockBean private UpdateEdificioUseCase updateEdificioUseCase;
  @MockBean private DeleteEdificioUseCase deleteEdificioUseCase;
  @MockBean private GetEdificioByIdUseCase getEdificioByIdUseCase;
  @MockBean private GetAllEdificiosUseCase getAllEdificiosUseCase;
  @MockBean private JwtTokenService jwtTokenService;

  private static EdificioModel sampleModel() {
    return EdificioApplicationMapper.fromCreateCommandToModel(
        new CreateEdificioCommand(
            ID,
            "Torre Del Mar",
            3600.0,
            48.0,
            16,
            32,
            2,
            "Parqueadero Mar",
            1,
            "Colombia",
            "Bolívar",
            "Cartagena",
            true,
            500000.0,
            true));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void shouldCreateEdificioAndReturn201() throws Exception {
    final String body =
        """
        {
          "id": "edf-001",
          "nombre": "Torre Del Mar",
          "metrosCuadrados": 3600.0,
          "altura": 48.0,
          "numPisos": 16,
          "numApartamentos": 32,
          "numOficinas": 2,
          "nombreParqueadero": "Parqueadero Mar",
          "numPiscinas": 1,
          "pais": "Colombia",
          "departamento": "Bolívar",
          "ciudad": "Cartagena",
          "tieneAscensor": true,
          "valorAdministracion": 500000.0,
          "tieneZonaSocial": true
        }
        """;

    when(createEdificioUseCase.execute(
            new CreateEdificioCommand(
                ID,
                "Torre Del Mar",
                3600.0,
                48.0,
                16,
                32,
                2,
                "Parqueadero Mar",
                1,
                "Colombia",
                "Bolívar",
                "Cartagena",
                true,
                500000.0,
                true)))
        .thenReturn(sampleModel());

    mockMvc
        .perform(post("/api/edificios").contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(ID))
        .andExpect(jsonPath("$.nombre").value("Torre Del Mar"))
        .andExpect(jsonPath("$.totalUnidades").value(34))
        .andExpect(jsonPath("$.alturaPorPiso").value(3.0));
  }

  @Test
  @WithMockUser(roles = "REVIEWER")
  void shouldListAllEdificios() throws Exception {
    when(getAllEdificiosUseCase.execute()).thenReturn(List.of(sampleModel()));

    mockMvc
        .perform(get("/api/edificios"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(ID))
        .andExpect(jsonPath("$[0].ciudad").value("Cartagena"));
  }

  @Test
  @WithMockUser(roles = "MEMBER")
  void shouldGetEdificioById() throws Exception {
    when(getEdificioByIdUseCase.execute(new GetEdificioByIdQuery(ID))).thenReturn(sampleModel());

    mockMvc
        .perform(get("/api/edificios/{id}", ID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(ID))
        .andExpect(jsonPath("$.nombre").value("Torre Del Mar"));
  }

  @Test
  @WithMockUser(roles = "MEMBER")
  void shouldReturn404WhenEdificioNotFound() throws Exception {
    when(getEdificioByIdUseCase.execute(new GetEdificioByIdQuery("edf-404")))
        .thenThrow(EdificioNotFoundException.becauseIdWasNotFound("edf-404"));

    mockMvc
        .perform(get("/api/edificios/{id}", "edf-404"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void shouldUpdateEdificio() throws Exception {
    final String body =
        """
        {
          "nombre": "Torre Del Mar",
          "metrosCuadrados": 3600.0,
          "altura": 48.0,
          "numPisos": 16,
          "numApartamentos": 32,
          "numOficinas": 2,
          "nombreParqueadero": "Parqueadero Mar",
          "numPiscinas": 1,
          "pais": "Colombia",
          "departamento": "Bolívar",
          "ciudad": "Cartagena",
          "tieneAscensor": true,
          "valorAdministracion": 500000.0,
          "tieneZonaSocial": true
        }
        """;

    when(updateEdificioUseCase.execute(
            new UpdateEdificioCommand(
                ID,
                "Torre Del Mar",
                3600.0,
                48.0,
                16,
                32,
                2,
                "Parqueadero Mar",
                1,
                "Colombia",
                "Bolívar",
                "Cartagena",
                true,
                500000.0,
                true)))
        .thenReturn(sampleModel());

    mockMvc
        .perform(
            put("/api/edificios/{id}", ID).contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(ID));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void shouldDeleteEdificio() throws Exception {
    mockMvc.perform(delete("/api/edificios/{id}", ID)).andExpect(status().isNoContent());

    verify(deleteEdificioUseCase).execute(new DeleteEdificioCommand(ID));
  }
}
