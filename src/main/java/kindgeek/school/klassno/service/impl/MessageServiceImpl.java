package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Message;
import kindgeek.school.klassno.entity.dto.MessageDto;
import kindgeek.school.klassno.entity.request.MessageRequest;
import kindgeek.school.klassno.exception.NotFoundException;
import kindgeek.school.klassno.mapper.MessageMapper;
import kindgeek.school.klassno.repository.MessageRepository;
import kindgeek.school.klassno.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;
    private final MessageRepository messageRepository;

    @Override
    public Long create(MessageRequest messageRequest) {
        Message message = messageMapper.toEntity(messageRequest);
        message.setMessageTime(LocalDateTime.now());
        messageRepository.save(message);
        return message.getId();
    }

    @Override
    public MessageDto findById(Long id) {
        Message message = messageRepository.findById(id)
            .orElseThrow(()->new NotFoundException("Message with id:" + id + " does not exist"));;
        return messageMapper.toDto(message);
    }

    @Override
    public Page<MessageDto> findByChatId(Long chatId, Pageable page){
        Page<Message> messages = messageRepository.findByChatId(chatId, page);
        return messages.map(messageMapper::toDto);
    }
}
