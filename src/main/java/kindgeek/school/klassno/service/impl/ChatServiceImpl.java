package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Chat;
import kindgeek.school.klassno.entity.Message;
import kindgeek.school.klassno.entity.dto.ChatDto;
import kindgeek.school.klassno.entity.dto.MessageDto;
import kindgeek.school.klassno.entity.request.ChatRequest;
import kindgeek.school.klassno.entity.request.MessageRequest;
import kindgeek.school.klassno.exception.NotFoundException;
import kindgeek.school.klassno.mapper.ChatMapper;
import kindgeek.school.klassno.repository.ChatRepository;
import kindgeek.school.klassno.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    private final ChatMapper chatMapper;

    @Override
    public Long create(ChatRequest chatRequest) {
        Chat chat =chatMapper.toEntity(chatRequest);
        chatRepository.save(chat);
        return chat.getId();
    }

    @Override
    public ChatDto findById(Long id) {
        Chat chat = chatRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Chat with id:" + id + " does not exist"));;
        return chatMapper.toDto(chat);
    }

    @Override
    public Page<ChatDto> findByUserId(Long userId, Pageable page){
        Page<Chat> chats = chatRepository.findByUserId(userId, page);
        return chats.map(chatMapper::toDto);
    }
}
