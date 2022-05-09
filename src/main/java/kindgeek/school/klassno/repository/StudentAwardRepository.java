package kindgeek.school.klassno.repository;

import kindgeek.school.klassno.entity.StudentAward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentAwardRepository extends JpaRepository<StudentAward, Long> {
    List<StudentAward> findByStudentIdOrderByDate(Long studentId);
}

