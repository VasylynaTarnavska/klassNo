package kindgeek.school.klassno.entity.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class QuizzRequest {

    @NotBlank
    private String name;

    @NotNull
    private Long lessonId;
}
