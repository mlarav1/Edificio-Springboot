package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateEdificioRestRequest(
    @NotBlank(message = "id must not be blank") String id,
    @NotBlank(message = "nombre must not be blank")
        @Size(min = 3, max = 120, message = "nombre must have between 3 and 120 characters")
        String nombre,
    @Positive(message = "metrosCuadrados must be positive") double metrosCuadrados,
    @Positive(message = "altura must be positive") double altura,
    @Min(value = 1, message = "numPisos must be at least 1") int numPisos,
    @PositiveOrZero(message = "numApartamentos must not be negative") int numApartamentos,
    @PositiveOrZero(message = "numOficinas must not be negative") int numOficinas,
    @NotBlank(message = "nombreParqueadero must not be blank")
        @Size(
            min = 3,
            max = 100,
            message = "nombreParqueadero must have between 3 and 100 characters")
        String nombreParqueadero,
    @PositiveOrZero(message = "numPiscinas must not be negative") int numPiscinas,
    @NotBlank(message = "pais must not be blank")
        @Size(min = 2, max = 100, message = "pais must have between 2 and 100 characters")
        String pais,
    @NotBlank(message = "departamento must not be blank")
        @Size(min = 2, max = 100, message = "departamento must have between 2 and 100 characters")
        String departamento,
    @NotBlank(message = "ciudad must not be blank")
        @Size(min = 2, max = 100, message = "ciudad must have between 2 and 100 characters")
        String ciudad,
    boolean tieneAscensor,
    @PositiveOrZero(message = "valorAdministracion must not be negative")
        double valorAdministracion,
    boolean tieneZonaSocial) {}
