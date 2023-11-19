package kindgeek.school.klassno.entity.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AnswerRequest {

    @NotBlank
    private String answerOption;

    @NotBlank
    private Boolean isCorrect = false;
}
