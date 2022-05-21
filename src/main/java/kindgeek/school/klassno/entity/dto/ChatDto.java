package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatDto {

    private Long id;

    private TeacherDto teacher;

    private StudentDto student;
}
