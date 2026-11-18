package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.valueobject.EdificioId;

public interface DeleteEdificioPort {
  void delete(EdificioId id);
}
