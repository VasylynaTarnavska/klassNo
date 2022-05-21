package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDto {

    private Long id;

    private String lessonLink;

    private String firstName;

    private String lastName;

    private String avatar;

    private String email;

}
