package kindgeek.school.klassno.entity.request;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AttendanceRequest {

    private Boolean isPresent = false;

    private String homeWork;

    @NotNull
    private Long lessonId;

    @NotNull
    private Long studentId;

}
