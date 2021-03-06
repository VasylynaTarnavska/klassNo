package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
public class AttendanceDto {

    private Long id;

    @NotNull
    private Boolean isPresent = false;

    private String homeWork;

    private MarkDto mark;

    private StudentDto student;

    private Set<HomeworkFileDto> files;
}
