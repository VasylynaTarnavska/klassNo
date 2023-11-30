package kindgeek.school.klassno.entity.dto.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LessonCriteria {

    private Long classRoomId;

    private Long subjectId;

    private Long teacherId;
}
