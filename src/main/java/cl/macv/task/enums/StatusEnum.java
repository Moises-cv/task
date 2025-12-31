package cl.macv.task.enums;

import java.util.EnumSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum StatusEnum {
    PENDIENTE("Pendiente"),
    EN_PROGRESO("En Progreso"),
    COMPLETADA("Completada"),
    CANCELADA("Cancelada"),
    EN_ESPERA("En Espera");

    private final String value;

    private StatusEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    /*
    * Transiciones válidas de los estados
    */
    public Set<StatusEnum> getValidTransitions() {
        return switch (this) {
            case PENDIENTE -> EnumSet.of(EN_PROGRESO, CANCELADA, EN_ESPERA);
            case EN_PROGRESO -> EnumSet.of(COMPLETADA, CANCELADA, EN_ESPERA);
            case EN_ESPERA -> EnumSet.of(EN_PROGRESO, CANCELADA);
            case COMPLETADA -> EnumSet.noneOf(StatusEnum.class); // Estado final
            case CANCELADA -> EnumSet.noneOf(StatusEnum.class);  // Estado final
        };
    }

    /**
     * Valida si puede transicionar al nuevo estado
     */
    public boolean transitionsTo(StatusEnum newStatus) {
        return getValidTransitions().contains(newStatus);
    }

    public static StatusEnum fromValue(String value) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Valor de estado no válido: " + value);
    }
}
