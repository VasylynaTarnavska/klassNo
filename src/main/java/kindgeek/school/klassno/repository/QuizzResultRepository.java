package kindgeek.school.klassno.repository;

import kindgeek.school.klassno.entity.QuizzResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizzResultRepository extends JpaRepository<QuizzResult, Long> {

}
