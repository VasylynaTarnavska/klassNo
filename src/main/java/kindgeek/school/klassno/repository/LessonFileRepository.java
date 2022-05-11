package kindgeek.school.klassno.repository;

import kindgeek.school.klassno.entity.LessonFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonFileRepository extends JpaRepository<LessonFile, Long> {
}
