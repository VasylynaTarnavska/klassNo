package kindgeek.school.klassno.repository;
import kindgeek.school.klassno.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
}
