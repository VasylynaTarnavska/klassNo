package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
public class ClassRoomDto {
    private Long id;

    @NotBlank
    private UUID roomKey;

    @NotBlank
    private String grade;
}
