package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllEdificiosUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllEdificiosPort;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllEdificiosService implements GetAllEdificiosUseCase {

  private final GetAllEdificiosPort getAllEdificiosPort;

  @Override
  public List<EdificioModel> execute() {
    return getAllEdificiosPort.getAll();
  }
}
