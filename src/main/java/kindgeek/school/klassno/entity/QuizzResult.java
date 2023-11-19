package kindgeek.school.klassno.entity;


import kindgeek.school.klassno.entity.Attendance;
import kindgeek.school.klassno.entity.QuestionResult;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class QuizzResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer correctAnswersAmount;

    private BigDecimal result;

    @OneToOne(cascade = CascadeType.MERGE)
    private Attendance attendance;

    @ManyToOne
    private  Quizz quizz;

    @OneToMany(mappedBy = "quizzResult", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<QuestionResult> questionResults;
}
