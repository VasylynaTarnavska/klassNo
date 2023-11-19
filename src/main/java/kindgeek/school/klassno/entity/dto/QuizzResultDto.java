package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class QuizzResultDto {

    private Long id;

    private StudentDto student;

    private Integer numberOfQuestions;

    private Integer correctAnswersAmount;

    private BigDecimal result;
}
