package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class QuizzResultFullDto {

    private Long id;

    private Integer numberOfQuestions;

    private Integer correctAnswersAmount;

    private BigDecimal result;

    private List<QuestionFullDto> questions;
}
