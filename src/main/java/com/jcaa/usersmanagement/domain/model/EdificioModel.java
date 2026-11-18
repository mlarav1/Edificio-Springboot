package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.exception.InvalidEdificioFieldException;
import com.jcaa.usersmanagement.domain.valueobject.EdificioAltura;
import com.jcaa.usersmanagement.domain.valueobject.EdificioId;
import com.jcaa.usersmanagement.domain.valueobject.EdificioMetrosCuadrados;
import com.jcaa.usersmanagement.domain.valueobject.EdificioNombre;
import com.jcaa.usersmanagement.domain.valueobject.EdificioNombreParqueadero;
import com.jcaa.usersmanagement.domain.valueobject.EdificioNumApartamentos;
import com.jcaa.usersmanagement.domain.valueobject.EdificioNumOficinas;
import com.jcaa.usersmanagement.domain.valueobject.EdificioNumPiscinas;
import com.jcaa.usersmanagement.domain.valueobject.EdificioNumPisos;
import com.jcaa.usersmanagement.domain.valueobject.EdificioUbicacion;
import com.jcaa.usersmanagement.domain.valueobject.EdificioValorAdministracion;
import java.util.Objects;
import lombok.Value;

@Value
public class EdificioModel {

  private static final double MIN_HEIGHT_PER_FLOOR = 2.0;

  EdificioId id;
  EdificioNombre nombre;
  EdificioMetrosCuadrados metrosCuadrados;
  EdificioAltura altura;
  EdificioNumPisos numPisos;
  EdificioNumApartamentos numApartamentos;
  EdificioNumOficinas numOficinas;
  EdificioNombreParqueadero nombreParqueadero;
  EdificioNumPiscinas numPiscinas;
  EdificioUbicacion ubicacion;
  boolean tieneAscensor;
  EdificioValorAdministracion valorAdministracion;
  boolean tieneZonaSocial;

  public EdificioModel(
      final EdificioId id,
      final EdificioNombre nombre,
      final EdificioMetrosCuadrados metrosCuadrados,
      final EdificioAltura altura,
      final EdificioNumPisos numPisos,
      final EdificioNumApartamentos numApartamentos,
      final EdificioNumOficinas numOficinas,
      final EdificioNombreParqueadero nombreParqueadero,
      final EdificioNumPiscinas numPiscinas,
      final EdificioUbicacion ubicacion,
      final boolean tieneAscensor,
      final EdificioValorAdministracion valorAdministracion,
      final boolean tieneZonaSocial) {
    this.id = Objects.requireNonNull(id, "id cannot be null");
    this.nombre = Objects.requireNonNull(nombre, "nombre cannot be null");
    this.metrosCuadrados = Objects.requireNonNull(metrosCuadrados, "metrosCuadrados cannot be null");
    this.altura = Objects.requireNonNull(altura, "altura cannot be null");
    this.numPisos = Objects.requireNonNull(numPisos, "numPisos cannot be null");
    this.numApartamentos = Objects.requireNonNull(numApartamentos, "numApartamentos cannot be null");
    this.numOficinas = Objects.requireNonNull(numOficinas, "numOficinas cannot be null");
    this.nombreParqueadero =
        Objects.requireNonNull(nombreParqueadero, "nombreParqueadero cannot be null");
    this.numPiscinas = Objects.requireNonNull(numPiscinas, "numPiscinas cannot be null");
    this.ubicacion = Objects.requireNonNull(ubicacion, "ubicacion cannot be null");
    this.tieneAscensor = tieneAscensor;
    this.valorAdministracion =
        Objects.requireNonNull(valorAdministracion, "valorAdministracion cannot be null");
    this.tieneZonaSocial = tieneZonaSocial;

    ensureHasUnits(this.numApartamentos, this.numOficinas);
    ensureHeightIsConsistent(this.altura, this.numPisos);
  }

  public static EdificioModel create(
      final EdificioId id,
      final EdificioNombre nombre,
      final EdificioMetrosCuadrados metrosCuadrados,
      final EdificioAltura altura,
      final EdificioNumPisos numPisos,
      final EdificioNumApartamentos numApartamentos,
      final EdificioNumOficinas numOficinas,
      final EdificioNombreParqueadero nombreParqueadero,
      final EdificioNumPiscinas numPiscinas,
      final EdificioUbicacion ubicacion,
      final boolean tieneAscensor,
      final EdificioValorAdministracion valorAdministracion,
      final boolean tieneZonaSocial) {
    return new EdificioModel(
        id,
        nombre,
        metrosCuadrados,
        altura,
        numPisos,
        numApartamentos,
        numOficinas,
        nombreParqueadero,
        numPiscinas,
        ubicacion,
        tieneAscensor,
        valorAdministracion,
        tieneZonaSocial);
  }

  public int totalUnidades() {
    return numApartamentos.value() + numOficinas.value();
  }

  public double alturaPorPiso() {
    return Math.round((altura.value() / numPisos.value()) * 100.0) / 100.0;
  }

  private static void ensureHasUnits(
      final EdificioNumApartamentos numApartamentos, final EdificioNumOficinas numOficinas) {
    if (numApartamentos.value() + numOficinas.value() == 0) {
      throw InvalidEdificioFieldException.becauseBuildingHasNoUnits();
    }
  }

  private static void ensureHeightIsConsistent(
      final EdificioAltura altura, final EdificioNumPisos numPisos) {
    if (altura.value() / numPisos.value() < MIN_HEIGHT_PER_FLOOR) {
      throw InvalidEdificioFieldException.becauseHeightIsInconsistentWithFloors(
          MIN_HEIGHT_PER_FLOOR);
    }
  }
}
