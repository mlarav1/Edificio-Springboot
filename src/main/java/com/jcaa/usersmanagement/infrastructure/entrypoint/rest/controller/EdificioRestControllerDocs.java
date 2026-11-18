package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.infrastructure.config.OpenApiConfig;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateEdificioRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateEdificioRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.ApiErrorResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.EdificioRestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
    name = "Edificios",
    description = "Gestión de edificios (CRUDL): crear, listar, consultar, actualizar y eliminar.")
public interface EdificioRestControllerDocs {

  @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
  @Operation(
      summary = "Crear edificio",
      description =
          "Registra un nuevo edificio validando que tenga unidades, altura consistente por piso "
              + "y que la combinación nombre + ciudad no esté duplicada.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Edificio creado exitosamente.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = EdificioRestResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Datos de entrada inválidos o regla de dominio incumplida.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "409",
        description = "Ya existe un edificio con el mismo nombre en la misma ciudad.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  EdificioRestResponse create(@Valid @RequestBody CreateEdificioRestRequest request);

  @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
  @Operation(
      summary = "Listar todos los edificios",
      description = "Retorna la lista de edificios ordenada alfabéticamente por nombre.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Lista de edificios obtenida exitosamente.",
        content =
            @Content(
                mediaType = "application/json",
                array =
                    @ArraySchema(schema = @Schema(implementation = EdificioRestResponse.class))))
  })
  List<EdificioRestResponse> getAll();

  @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
  @Operation(
      summary = "Obtener edificio por ID",
      description = "Retorna los datos de un edificio específico identificado por su ID único.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Edificio encontrado.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = EdificioRestResponse.class))),
    @ApiResponse(
        responseCode = "404",
        description = "No existe ningún edificio con el ID proporcionado.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  EdificioRestResponse getById(
      @Parameter(description = "ID único del edificio.", example = "edf-001", required = true)
          @PathVariable
          String id);

  @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
  @Operation(
      summary = "Actualizar edificio",
      description = "Actualiza los datos de un edificio existente validando reglas de negocio.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Edificio actualizado exitosamente.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = EdificioRestResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Datos de entrada inválidos.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "404",
        description = "No existe ningún edificio con el ID proporcionado.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "409",
        description = "El nombre y ciudad ya están registrados en otro edificio.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  EdificioRestResponse update(
      @Parameter(
              description = "ID único del edificio a actualizar.",
              example = "edf-001",
              required = true)
          @PathVariable
          String id,
      @Valid @RequestBody UpdateEdificioRestRequest request);

  @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
  @Operation(
      summary = "Eliminar edificio",
      description = "Elimina permanentemente un edificio del sistema por su ID.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Edificio eliminado exitosamente."),
    @ApiResponse(
        responseCode = "404",
        description = "No existe ningún edificio con el ID proporcionado.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  void delete(
      @Parameter(
              description = "ID único del edificio a eliminar.",
              example = "edf-001",
              required = true)
          @PathVariable
          String id);
}
