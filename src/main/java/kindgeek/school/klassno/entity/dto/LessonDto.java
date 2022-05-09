package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LessonDto {

    private Long id;

    private LocalDateTime lessonTime;

    private String topic;

    private String description;
}
