package kindgeek.school.klassno.entity.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class StudentAwardRequest {

    @NotNull
    private LocalDate date;

    @NotNull
    private Long awardId;

    @NotNull
    private Long studentId;

    @NotNull
    private Long teacherId;
}
