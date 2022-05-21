package kindgeek.school.klassno.entity.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class ClassRoomRequest {

    @NotBlank
    private String grade;

    @NotBlank
    private String className;
}
