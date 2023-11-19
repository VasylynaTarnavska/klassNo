package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionFullDto extends QuestionDto{

    private QuestionResultDto questionResult;

}
