package kindgeek.school.klassno.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private ClassRoom classRoom;

    @ManyToOne
    private Teacher teacher;

    @NotNull
    private LocalDateTime lessonTime;

    @NotBlank
    private String topic;

    private String description;

    @OneToMany(mappedBy = "lesson")
    private List<Attendance> attendances;

    @ElementCollection
    private List<String> files;
}
