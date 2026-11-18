package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.EdificioModel;
import com.jcaa.usersmanagement.domain.valueobject.EdificioNombre;
import com.jcaa.usersmanagement.domain.valueobject.EdificioUbicacion;
import java.util.Optional;

public interface GetEdificioByNombreAndCiudadPort {
  Optional<EdificioModel> getByNombreAndCiudad(
      EdificioNombre nombre, EdificioUbicacion ubicacion);
}
