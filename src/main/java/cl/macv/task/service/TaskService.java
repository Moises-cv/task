package cl.macv.task.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.macv.task.dto.CreateTaskRequest;
import cl.macv.task.dto.TaskResponse;
import cl.macv.task.enums.StatusEnum;
import cl.macv.task.exception.InvalidStatusTransitionException;
import cl.macv.task.exception.TaskNotFoundException;
import cl.macv.task.mapper.TaskMapper;
import cl.macv.task.model.Task;
import cl.macv.task.repository.TaskRepository;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    @Timed(value = "task.create.time", description = "Tiempo para crear tarea")
    public TaskResponse createTask(CreateTaskRequest taskRequest) {
        log.info("Iniciando Service | createTask");
        
        Task task = taskMapper.toEntity(taskRequest);
        task.setStatusEnum(StatusEnum.PENDIENTE);
        task.setCreateAt(LocalDateTime.now());

        Task saveTask = taskRepository.save(task);

        log.info("Tarea creada {}", task);

        return taskMapper.toResponse(saveTask);
    }

    @Transactional
    @Timed(value = "task.updateStatus.time", description = "Tiempo para actualizar estado")
    public TaskResponse updateStatus(Long id, StatusEnum newStatus) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException("Tarea no encontrada: " + id));

        StatusEnum currentStatus = task.getStatusEnum();

        if (!currentStatus.transitionsTo(newStatus)) {
            throw new InvalidStatusTransitionException(currentStatus, newStatus);
        }

        task.setStatusEnum(newStatus);
        Task updateTask = taskRepository.save(task);

        return taskMapper.toResponse(updateTask);
    }
    
    @Transactional(readOnly = true)
    @Timed(value = "task.getById.time", description = "Tiempo para obtener tarea por ID")
    public Optional<TaskResponse> getTaskById(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException("Tarea no encontrada: " + id));

        return Optional.of(taskMapper.toResponse(task));
    }
}
