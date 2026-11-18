package com.jcaa.usersmanagement.domain.event;

import com.jcaa.usersmanagement.domain.model.EdificioModel;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public final class EdificioUpdatedDomainEvent extends DomainEvent {

  private static final String EVENT_NAME = "edificio.updated";

  private final EdificioModel edificio;

  public EdificioUpdatedDomainEvent(final EdificioModel edificio) {
    super(EVENT_NAME);
    this.edificio = edificio;
  }

  @Override
  public Map<String, String> payload() {
    final Map<String, String> data = new LinkedHashMap<>();
    data.put("id", edificio.getId().value());
    data.put("nombre", edificio.getNombre().value());
    data.put("ciudad", edificio.getUbicacion().ciudad());
    data.put("pais", edificio.getUbicacion().pais());
    return Map.copyOf(data);
  }
}
