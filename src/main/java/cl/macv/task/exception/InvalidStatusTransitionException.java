package cl.macv.task.exception;

import cl.macv.task.enums.StatusEnum;

public class InvalidStatusTransitionException extends RuntimeException {

    public InvalidStatusTransitionException(StatusEnum status, StatusEnum newStatus) {
        super(String.format("Trasición no permitida: No puede pasar de '%s' a '%s'", status.getValue(), newStatus.getValue()));
    }

}
