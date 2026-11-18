package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.EdificioModel;
import java.util.List;

public interface GetAllEdificiosPort {
  List<EdificioModel> getAll();
}
