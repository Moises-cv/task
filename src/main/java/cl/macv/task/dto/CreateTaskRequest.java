package cl.macv.task.dto;

import cl.macv.task.utils.Constant;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateTaskRequest {

    @NotBlank(message = Constant.NOT_BLANK)
    private String name;

    @NotBlank(message = Constant.NOT_BLANK)
    private String description;

}