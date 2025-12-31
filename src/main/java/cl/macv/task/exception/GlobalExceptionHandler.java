package cl.macv.task.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import cl.macv.task.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(ErrorResponse.builder().codigo(HttpStatus.METHOD_NOT_ALLOWED.value()).mensaje("Metodo no soportado").hora(LocalDateTime.now()).build());
    }

    /* VALIDACIONES DEL @REQUESTBODY CON @VALID (CUERPO DE LA PETICION) */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        List<String> errors = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(ex -> ex.getField() + ": " + ex.getDefaultMessage())
            .collect(Collectors.toList());
        
        errors.addAll(e.getBindingResult()
            .getGlobalErrors()
            .stream()
            .map(ex -> ex.getObjectName() + ": " + ex.getDefaultMessage())
            .toList());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.builder()
                .codigo(HttpStatus.BAD_REQUEST.value())
                .mensaje("Error de validación")
                .hora(LocalDateTime.now())
                .errores(errors)
                .build());
    }

    /* VALIDACIONES DE @PATHVARIABLE O @REQUESTPARAM O SERVICIOS CON @VALIDATED, PARAMETROS INDIVIDUALES CON @VALIDATED EN LA CLASE (JAKARTA) */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException e) {

        List<String> errors = e.getConstraintViolations()
            .stream()
            .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
            .toList();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.builder()
                .codigo(HttpStatus.BAD_REQUEST.value())
                .mensaje("Error de validación")
                .hora(LocalDateTime.now())
                .errores(errors)
                .build());
    }

    /* JSON mal formado / enum inválido */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMensajeNotReadable(HttpMessageNotReadableException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.builder()
                .codigo(HttpStatus.BAD_REQUEST.value())
                .mensaje("Error de validación " + e.getMessage())
                .hora(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgTypeMismatch(MethodArgumentTypeMismatchException e) {

        String errors = e.getPropertyName();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.builder()
            .codigo(HttpStatus.BAD_REQUEST.value())
            .mensaje("Error " + errors)
            .hora(LocalDateTime.now())
            .build());
    }

    @ExceptionHandler(InvalidStatusTransitionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidStatusTransition(InvalidStatusTransitionException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.builder()
            .codigo(HttpStatus.BAD_REQUEST.value())
            .mensaje("Error " + e.getMessage())
            .hora(LocalDateTime.now())
            .build());
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFound(TaskNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse.builder()
            .codigo(HttpStatus.NOT_FOUND.value())
            .mensaje("Error " + e.getMessage())
            .hora(LocalDateTime.now())
            .build());
    }
}
