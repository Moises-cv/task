package cl.macv.task.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponse{
    private Integer codigo;
    private String mensaje;
    private LocalDateTime hora;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errores;
}
