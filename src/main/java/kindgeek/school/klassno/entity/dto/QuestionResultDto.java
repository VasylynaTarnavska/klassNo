package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionResultDto {

    private Long id;

    private Boolean isCorrect;

    private AnswerDto selectedAnswer;

}
