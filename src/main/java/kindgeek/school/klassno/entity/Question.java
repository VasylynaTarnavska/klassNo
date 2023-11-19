package kindgeek.school.klassno.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String questionText;

    @ManyToOne
    private Quizz quizz;

    @OneToMany(mappedBy = "question")
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private List<Answer> answers;

    @OneToMany(mappedBy = "question")
    private List<QuestionResult> results;

    @Transient
    private QuestionResult studentResult;
}
