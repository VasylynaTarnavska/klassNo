package kindgeek.school.klassno.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Quizz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @OneToOne
    @NotNull
    private Lesson lesson;

    @OneToMany(mappedBy = "quizz", cascade = {CascadeType.MERGE,CascadeType.REMOVE}, orphanRemoval = true)
    private List<Question> questions;

    @OneToMany(mappedBy = "quizz", cascade = CascadeType.MERGE)
    private List<QuizzResult> quizzResults;

    private Integer numberOfQuestions;

}


