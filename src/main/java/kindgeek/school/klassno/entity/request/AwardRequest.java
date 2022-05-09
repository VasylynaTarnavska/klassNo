package kindgeek.school.klassno.entity.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AwardRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String logo;
}
