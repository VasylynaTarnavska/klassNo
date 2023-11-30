package kindgeek.school.klassno.repository;

import kindgeek.school.klassno.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>, JpaSpecificationExecutor<Lesson> {

    @Query("""
            select l from Lesson l
            join l.classRoom cl 
            join cl.students st
            where st.id = :studentId """)
    List<Lesson> findByStudentId(Long studentId);

    @Query(value = """
            select lesson.*
            from lesson
            left join class_room cr on lesson.class_room_id = cr.id
            left join subject s on lesson.subject_id = s.id
            where cr.id = :classId
              and s.id = :subjectId
            order by lesson.lesson_time desc""", nativeQuery = true)
    List<Lesson> findByClassIdAndSubjectId(Long classId, Long subjectId);

    @Query(value = """
            select lesson.*
            from lesson
            left join teacher on lesson.teacher_id=teacher.id
            where teacher.id  = :teacherId """,  nativeQuery = true)
    List<Lesson> findByTeacherId(Long teacherId);
}
