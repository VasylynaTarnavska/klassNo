package kindgeek.school.klassno.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AttendanceShortDto {

    private Long id;

    private Long lessonId;

    private LocalDateTime lessonTime;

    private String topic;

    private boolean isPresent;

    private MarkDto mark;
}
