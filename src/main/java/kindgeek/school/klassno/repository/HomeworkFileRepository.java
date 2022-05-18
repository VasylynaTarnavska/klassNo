package kindgeek.school.klassno.repository;

import kindgeek.school.klassno.entity.HomeworkFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkFileRepository extends JpaRepository<HomeworkFile, Long> {
}
