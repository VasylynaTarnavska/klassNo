package kindgeek.school.klassno.repository;
import kindgeek.school.klassno.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByLogin(String login);
}
