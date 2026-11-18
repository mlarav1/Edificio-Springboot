package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.EdificioModel;

public interface SaveEdificioPort {
  EdificioModel save(EdificioModel edificio);
}
