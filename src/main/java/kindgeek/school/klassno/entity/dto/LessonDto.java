package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class LessonDto {

    private Long id;

    private LocalDateTime lessonTime;

    private String classGrade;

    private String subjectName;

    private String topic;

    private String description;

    private String homework;

    private Set<LessonFileDto> files;

    private Byte mark;

    private String lessonLink;
}
