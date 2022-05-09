package kindgeek.school.klassno.entity.dto;

import kindgeek.school.klassno.entity.Lesson;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubjectDto {

    private Long id;

    private String subjectName;

    private List<LessonDto> lessons;
}

