package kindgeek.school.klassno.entity.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MarkRequest {

    @NotBlank
    private String level;

    private String comment;

    @NotNull
    private Long studentId;

    @NotNull
    private Long attendanceId;
}
