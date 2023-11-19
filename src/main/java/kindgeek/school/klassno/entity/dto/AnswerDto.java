package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDto {

    private Long id;

    private String answerOption;

    private Boolean isCorrect;
}
