package kindgeek.school.klassno.entity.dto;

import kindgeek.school.klassno.entity.ClassRoom;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String avatar;

//    private ClassRoom classRoom;
}

