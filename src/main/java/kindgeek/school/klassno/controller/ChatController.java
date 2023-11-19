package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.dto.ChatDto;
import kindgeek.school.klassno.entity.dto.MessageDto;
import kindgeek.school.klassno.entity.request.ChatRequest;
import kindgeek.school.klassno.entity.request.MessageRequest;
import kindgeek.school.klassno.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public Long create(@RequestBody ChatRequest chatRequest){
        log.info("Creating new chat");
        return chatService.create(chatRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public ChatDto findById (@PathVariable Long id){
        log.info("Getting chat by id: {}", id);
        return chatService.findById(id);
    }

    @GetMapping("/by-user/{userId}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public Page<ChatDto> findByUserId (@PathVariable Long userId, Pageable page){
        log.info("Getting chat by user id: {}", userId);
        return chatService.findByUserId(userId, page);
    }
}
