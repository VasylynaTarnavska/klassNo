package kindgeek.school.klassno.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Student extends User {

    @NotNull
    @ManyToOne
    private ClassRoom classRoom;

    @OneToMany(mappedBy = "student")
    private List<Mark> marks;

    @OneToMany(mappedBy = "student")
    private List<Attendance> attendances;

    @OneToMany(mappedBy = "student")
    private List<StudentAward> studentAwards;

    @OneToMany(mappedBy = "student")
    private List<Chat> chats;

    public void setAttendancesBySubjectId(Long subjectId) {
        List<Attendance> attendanceForSubject = attendances.stream()
                .filter(attendance -> Objects.equals(subjectId, attendance.getLesson().getSubject().getId()))
                .sorted(Comparator.comparing(attendance -> attendance.getLesson().getLessonTime()))
                .toList();
        attendances.clear();
        attendances = attendanceForSubject;
    }
}
