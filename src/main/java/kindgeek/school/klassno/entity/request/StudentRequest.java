package kindgeek.school.klassno.entity.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StudentRequest {

    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String avatar;

    private Long classRoomId;
}
