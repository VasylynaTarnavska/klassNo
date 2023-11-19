package kindgeek.school.klassno.repository;

import kindgeek.school.klassno.entity.Quizz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizzRepository extends JpaRepository<Quizz, Long> {

    @Query(value = """
            select quizz.* from quizz
            join lesson on quizz.lesson_id = lesson.id
            join class_room class on lesson.class_room_id = class.id
            join student on student.class_room_id = class.id
            where student.id = :studentId""", nativeQuery = true)
    List<Quizz> findByStudentId(Long studentId);
}
