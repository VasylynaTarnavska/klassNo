package kindgeek.school.klassno.entity.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class QuestionRequest {

    @NotNull
    private Long quizzId;

    @NotBlank
    private String questionText;

    @NotEmpty
    private List<AnswerRequest> answers;
}
