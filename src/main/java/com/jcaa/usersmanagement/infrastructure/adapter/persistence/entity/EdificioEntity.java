package com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity;

public record EdificioEntity(
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
    String createdAt,
    String updatedAt) {}
