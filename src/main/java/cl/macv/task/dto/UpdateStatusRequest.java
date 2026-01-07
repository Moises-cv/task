package cl.macv.task.dto;

import cl.macv.task.enums.StatusEnum;
import cl.macv.task.utils.Constant;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusRequest {

    @NotNull(message = Constant.NOT_NULL)
    private StatusEnum status;

}
