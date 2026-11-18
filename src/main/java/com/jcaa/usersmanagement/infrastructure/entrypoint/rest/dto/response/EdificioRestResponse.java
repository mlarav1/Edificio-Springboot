package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response;

public record EdificioRestResponse(
    String id,
    String nombre,
    double metrosCuadrados,
    double altura,
    int numPisos,
    int numApartamentos,
    int numOficinas,
    String nombreParqueadero,
    int numPiscinas,
    String pais,
    String departamento,
    String ciudad,
    boolean tieneAscensor,
    double valorAdministracion,
    boolean tieneZonaSocial,
    int totalUnidades,
    double alturaPorPiso) {}
