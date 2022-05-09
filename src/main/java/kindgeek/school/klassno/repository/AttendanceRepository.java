package kindgeek.school.klassno.repository;

import kindgeek.school.klassno.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query ("select a from Attendance a where a.lesson.id = :lessonId " +
            "order by a.student.lastName, a.student.firstName")
    List<Attendance> findByLessonId(Long lessonId);
}
