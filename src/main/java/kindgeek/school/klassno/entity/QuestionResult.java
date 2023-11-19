package kindgeek.school.klassno.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class QuestionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isCorrect;

    private Answer selectedAnswer;

    @ManyToOne
    private Question question;

    @ManyToOne
    private QuizzResult quizzResult;

}
