package kindgeek.school.klassno.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import java.util.List;

@Getter
@Setter
@Entity
public class Teacher extends User{

    @Email
    private String email;

    private String lessonLink;

    @OneToMany (mappedBy = "teacher")
    private List<Lesson> lessons;

    @OneToMany (mappedBy = "teacher")
    private List<StudentAward> studentAwards;
}
