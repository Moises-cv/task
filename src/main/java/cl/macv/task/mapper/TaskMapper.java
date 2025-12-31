package cl.macv.task.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cl.macv.task.dto.CreateTaskRequest;
import cl.macv.task.dto.TaskResponse;
import cl.macv.task.model.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    // Task -> TaskResponse
    TaskResponse toResponse(Task task);

    // CreateTaskRequest -> Task
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "statusEnum", ignore = true)
    Task toEntity(CreateTaskRequest request);

}
