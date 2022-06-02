package kindgeek.school.klassno.entity.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class ChatRequest {

    private Long id;

    @NotNull
    private Long teacherId;

    @NotNull
    private Long studentId;

}
