package cl.macv.task.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import cl.macv.task.dto.CreateTaskRequest;
import cl.macv.task.dto.TaskResponse;
import cl.macv.task.dto.UpdateStatusRequest;
import cl.macv.task.service.TaskService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TaskController implements TaskControllerApi {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ResponseEntity<TaskResponse> createTask(CreateTaskRequest taskRequest, UriComponentsBuilder uriComponents) {
        log.info("Iniciando controller | crear task");

        TaskResponse taskResponse = taskService.createTask(taskRequest);

        log.info("retornando {}", taskResponse);

        URI location = uriComponents
            .path("/api/v1/task/{id}")
            .buildAndExpand(taskResponse.getId())
            .toUri();

        return ResponseEntity.created(location).body(taskResponse);
    }

    @Override
    public ResponseEntity<TaskResponse> updateStatusTask(Long id, UpdateStatusRequest statusRequest) {
        log.info("Iniciando controller | actualizar estado");

        TaskResponse taskResponse = taskService.updateStatus(id, statusRequest.getStatus());

        return ResponseEntity.ok(taskResponse);
    }

    @Override
    public ResponseEntity<TaskResponse> getTaskById(Long id) {
        log.info("Iniciando controller | buscar por id");

        Optional<TaskResponse> taskResponse = taskService.getTaskById(id);
        
        return ResponseEntity.ok(taskResponse.get());
    }

    
    
}
