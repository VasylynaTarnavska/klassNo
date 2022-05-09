package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AwardDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String logo;
}
