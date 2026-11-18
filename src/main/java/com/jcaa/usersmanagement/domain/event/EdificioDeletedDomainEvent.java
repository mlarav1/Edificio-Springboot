package com.jcaa.usersmanagement.domain.event;

import com.jcaa.usersmanagement.domain.valueobject.EdificioId;
import java.util.Map;
import lombok.Getter;

@Getter
public final class EdificioDeletedDomainEvent extends DomainEvent {

  private static final String EVENT_NAME = "edificio.deleted";

  private final EdificioId edificioId;

  public EdificioDeletedDomainEvent(final EdificioId edificioId) {
    super(EVENT_NAME);
    this.edificioId = edificioId;
  }

  @Override
  public Map<String, String> payload() {
    return Map.of("id", edificioId.value());
  }
}
