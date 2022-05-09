package kindgeek.school.klassno.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Boolean isPresent = false;

    private String homeWork;

    @ManyToOne
    private Lesson lesson;

    @ManyToOne
    private Student student;

    @OneToOne(mappedBy = "attendance")
    private Mark mark;
}
