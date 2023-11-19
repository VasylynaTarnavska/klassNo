package kindgeek.school.klassno.repository;

import kindgeek.school.klassno.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

    @Query("""
            select m from Mark m 
            join m.student st 
            join m.attendance a 
            join a.lesson l 
            join l.subject s 
            where st.id = :studentId
            order by s.subjectName, l.lessonTime""")
    List<Mark> findByStudentId(Long studentId);

    @Query("""
            select m from Mark m 
            join m.attendance a 
            join a.lesson l 
            join m.student s
            where l.subject.id = :subjectId
            and s.classRoom.id = :classRoomId
            order by l.lessonTime, s.lastName, s.firstName
            """)
    List<Mark> findBySubjectIdAndClassRoomId(Long subjectId, Long classRoomId);
}
