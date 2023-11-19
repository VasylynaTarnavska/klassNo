package kindgeek.school.klassno.service;

import kindgeek.school.klassno.entity.dto.ChatDto;
import kindgeek.school.klassno.entity.request.ChatRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatService {
    Long create(ChatRequest chatRequest);

    ChatDto findById(Long id);

    Page<ChatDto> findByUserId(Long userId, Pageable page);
}
