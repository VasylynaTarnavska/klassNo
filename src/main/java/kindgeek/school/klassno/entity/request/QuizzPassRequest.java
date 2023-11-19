package kindgeek.school.klassno.entity.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizzPassRequest {

    @NotNull
    private Long quizzId;

    @NotNull
    private Long studentId;

    @NotEmpty
    private List<QuestionPassRequest> questionPassRequests;

}
