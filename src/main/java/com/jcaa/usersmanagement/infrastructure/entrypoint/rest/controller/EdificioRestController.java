package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.application.port.in.CreateEdificioUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteEdificioUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllEdificiosUseCase;
import com.jcaa.usersmanagement.application.port.in.GetEdificioByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateEdificioUseCase;
import com.jcaa.usersmanagement.application.service.dto.command.CreateEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEdificioCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetEdificioByIdQuery;
import com.jcaa.usersmanagement.domain.model.EdificioModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateEdificioRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateEdificioRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.EdificioRestResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper.EdificioRestMapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/edificios")
@RequiredArgsConstructor
public class EdificioRestController implements EdificioRestControllerDocs {

  private final CreateEdificioUseCase createEdificioUseCase;
  private final UpdateEdificioUseCase updateEdificioUseCase;
  private final DeleteEdificioUseCase deleteEdificioUseCase;
  private final GetEdificioByIdUseCase getEdificioByIdUseCase;
  private final GetAllEdificiosUseCase getAllEdificiosUseCase;

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public EdificioRestResponse create(@Valid @RequestBody final CreateEdificioRestRequest request) {
    final CreateEdificioCommand command = EdificioRestMapper.toCreateCommand(request);
    final EdificioModel edificio = createEdificioUseCase.execute(command);
    return EdificioRestMapper.toResponse(edificio);
  }

  @Override
  @GetMapping
  public List<EdificioRestResponse> getAll() {
    return EdificioRestMapper.toResponseList(getAllEdificiosUseCase.execute());
  }

  @Override
  @GetMapping("/{id}")
  public EdificioRestResponse getById(@PathVariable final String id) {
    final GetEdificioByIdQuery query = EdificioRestMapper.toGetByIdQuery(id);
    final EdificioModel edificio = getEdificioByIdUseCase.execute(query);
    return EdificioRestMapper.toResponse(edificio);
  }

  @Override
  @PutMapping("/{id}")
  public EdificioRestResponse update(
      @PathVariable final String id, @Valid @RequestBody final UpdateEdificioRestRequest request) {
    final UpdateEdificioCommand command = EdificioRestMapper.toUpdateCommand(id, request);
    final EdificioModel edificio = updateEdificioUseCase.execute(command);
    return EdificioRestMapper.toResponse(edificio);
  }

  @Override
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable final String id) {
    final DeleteEdificioCommand command = EdificioRestMapper.toDeleteCommand(id);
    deleteEdificioUseCase.execute(command);
  }
}
