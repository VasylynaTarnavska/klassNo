package kindgeek.school.klassno.repository;

import kindgeek.school.klassno.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByChatId(Long ChatId, Pageable page);
}
