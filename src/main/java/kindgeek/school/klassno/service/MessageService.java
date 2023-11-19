package kindgeek.school.klassno.service;

import kindgeek.school.klassno.entity.dto.MessageDto;
import kindgeek.school.klassno.entity.request.MessageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    Long create(MessageRequest messageRequest);

    MessageDto findById(Long id);

    Page<MessageDto> findByChatId(Long chatId, Pageable page);
}
