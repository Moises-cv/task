package cl.macv.task.dto;

import cl.macv.task.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusRequest {

    private StatusEnum status;

}
