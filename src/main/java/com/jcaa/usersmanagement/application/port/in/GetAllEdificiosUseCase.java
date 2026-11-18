package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.EdificioModel;
import java.util.List;

public interface GetAllEdificiosUseCase {
  List<EdificioModel> execute();
}
