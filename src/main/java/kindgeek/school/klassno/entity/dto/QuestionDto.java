package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionDto {

    private Long id;

    private String questionText;

    private List<AnswerDto> answers;
}
