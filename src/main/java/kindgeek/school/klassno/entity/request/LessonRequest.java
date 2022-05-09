package kindgeek.school.klassno.entity.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class LessonRequest {

    @NotNull
    private Long subjectId;

    @NotNull
    private Long classRoomId;

    @NotNull
    private Long teacherId;

    @NotNull
        private LocalDateTime lessonTime;

    @NotBlank
    private String topic;

    private String description;
}
