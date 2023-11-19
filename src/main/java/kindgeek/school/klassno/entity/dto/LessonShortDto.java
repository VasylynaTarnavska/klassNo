package kindgeek.school.klassno.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LessonShortDto {

    private Long id;

    private LocalDateTime lessonTime;

    private String topic;
}
