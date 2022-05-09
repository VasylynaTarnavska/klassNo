package kindgeek.school.klassno.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
public class Student extends User{

    @NotNull
    @ManyToOne
    private ClassRoom classRoom;

    @OneToMany(mappedBy = "student")
    private List<Mark> marks;

    @OneToMany(mappedBy ="student")
    private List<Attendance> attendances;

    @OneToMany(mappedBy ="student")
    private List<StudentAward> studentAwards;
}
