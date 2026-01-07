package cl.macv.task.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import cl.macv.task.dto.CreateTaskRequest;
import cl.macv.task.dto.TaskResponse;
import cl.macv.task.dto.UpdateStatusRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RequestMapping("/api/v1/task")
public interface TaskControllerApi {

    @Operation(
        summary = "Crear tarea", //breve descripción de esta operación 120 caracteres.
        description = "Crea una tarea y retorna su representación" // descripción detallada de la operación.
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Tarea creada"),
        @ApiResponse(responseCode = "400", description = "Error de validaciones"),
        @ApiResponse(responseCode = "405", description = "Metodo no soportado")
    })
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
        @Valid
        @RequestBody
        CreateTaskRequest taskRequest,

        UriComponentsBuilder uriComponents
    );

    @Operation(
        summary = "Actualizar estado de una tarea",
        description = "Actualiza el estado de una tarea existente"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado de tarea actualizado"),
        @ApiResponse(responseCode = "400", description = "Error de validaciones"),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
        @ApiResponse(responseCode = "405", description = "Metodo no soportado")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateStatusTask(
        @PathVariable
        Long id,

        @Valid
        @RequestBody
        UpdateStatusRequest statusRequest
    );

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
        @PathVariable
        Long id
    );

}