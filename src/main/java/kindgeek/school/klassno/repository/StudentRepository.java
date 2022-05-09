package kindgeek.school.klassno.repository;

import kindgeek.school.klassno.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("select st from Student st " +
            "where st.classRoom.id = :classRoomId")
    Page<Student> findByClassRoomId (Long classRoomId, Pageable page);
}
