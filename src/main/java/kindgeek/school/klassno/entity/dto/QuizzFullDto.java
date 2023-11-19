package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuizzFullDto {

    private Long id;

    private String name;

    private LessonShortDto lesson;

    private List<QuestionDto> questions;

    private Integer numberOfQuestions;
}
