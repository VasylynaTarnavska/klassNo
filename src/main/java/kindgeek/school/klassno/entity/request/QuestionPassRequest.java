package kindgeek.school.klassno.entity.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionPassRequest {

    @NotNull
    private Long questionId;

    @NotNull
    private Long selectedAnswerId;

}
