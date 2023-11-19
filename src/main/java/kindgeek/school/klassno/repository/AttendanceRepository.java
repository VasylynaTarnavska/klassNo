package kindgeek.school.klassno.repository;

import kindgeek.school.klassno.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("""
            select a from Attendance a 
            where a.lesson.id = :lessonId
            order by a.student.lastName, a.student.firstName""")
    List<Attendance> findByLessonId(Long lessonId);

    @Query("""
            select mark.value
            from Mark mark
            left join mark.attendance att
            left join att.lesson lesson
            left join att.student student
            where lesson.id = :lessonId
            and student.id = :studentId """)
    Byte findMarkByStudentIdAndLessonId(Long studentId, Long lessonId);

    @Query("""
            select attendance
            from Attendance attendance
            left join attendance.student student
            left join attendance.lesson lesson
            left join lesson.quizz quizz
            where student.id = :studentId
            and quizz.id = :quizzId""")
    Optional<Attendance> findByStudentIdAndQuizzId(Long studentId, Long quizzId);
}
