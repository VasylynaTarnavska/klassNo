package kindgeek.school.klassno.repository;

import kindgeek.school.klassno.entity.Chat;
import kindgeek.school.klassno.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select ch from Chat ch " +
            "where ch.teacher.id = :userId "+
            "or ch.student.id = :userId")
    Page<Chat> findByUserId(Long userId, Pageable page);
}
