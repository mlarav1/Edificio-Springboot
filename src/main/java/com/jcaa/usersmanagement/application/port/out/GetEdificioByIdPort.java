package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.EdificioModel;
import com.jcaa.usersmanagement.domain.valueobject.EdificioId;
import java.util.Optional;

public interface GetEdificioByIdPort {
  Optional<EdificioModel> getById(EdificioId id);
}
