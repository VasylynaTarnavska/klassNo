package kindgeek.school.klassno.repository;

import kindgeek.school.klassno.entity.Lesson;
import kindgeek.school.klassno.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>, JpaSpecificationExecutor<Lesson> {

    @Query("select l from Lesson l " +
            "join l.classRoom cl "+
            "join cl.students st " +
            "where st.id = :studentId ")
    Page<Lesson> findLessonByStudentId (Long studentId, Pageable page);
}
