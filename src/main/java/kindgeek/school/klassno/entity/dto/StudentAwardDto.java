package kindgeek.school.klassno.entity.dto;

import kindgeek.school.klassno.entity.Award;
import kindgeek.school.klassno.entity.Student;
import kindgeek.school.klassno.entity.Teacher;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class StudentAwardDto {
    private Long id;

    private LocalDate date;

    private AwardDto award;

    private StudentDto student;

    private TeacherDto teacher;
}
