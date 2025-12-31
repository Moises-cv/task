package cl.macv.task.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException() {
        super("Tarea no encontrada");
    }

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(Long id) {
        super("Tarea no encontrada con id: " + id);
    }
}
