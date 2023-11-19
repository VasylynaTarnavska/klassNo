package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class QuizzStudentListDto {

    private Long id;

    private String name;

    private LessonShortDto lesson;

    private Integer numberOfQuestions;

    private Integer correctAnswersAmount;

    private BigDecimal result;
}
