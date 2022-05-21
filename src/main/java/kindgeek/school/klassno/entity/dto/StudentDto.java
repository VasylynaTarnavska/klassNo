package kindgeek.school.klassno.entity.dto;

import kindgeek.school.klassno.entity.ClassRoom;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StudentDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String avatar;

    private String classRoomId;

}

