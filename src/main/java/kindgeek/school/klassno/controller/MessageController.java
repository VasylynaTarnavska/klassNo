package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.dto.MessageDto;
import kindgeek.school.klassno.entity.request.MessageRequest;
import kindgeek.school.klassno.service.MessageService;
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
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public Long create(@RequestBody MessageRequest messageRequest){
        log.info("Creating new message");
        return messageService.create(messageRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public MessageDto findById (@PathVariable Long id){
        log.info("Getting message by id: {}", id);
        return messageService.findById(id);
    }

    @GetMapping("/by-chat/{chatId}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public Page<MessageDto> findByChatId (@PathVariable Long chatId,
                                              @SortDefault(sort = "messageTime", direction = Sort.Direction.ASC) Pageable page){
        log.info("Getting message by chat id: {}", chatId);
        return messageService.findByChatId(chatId, page);
    }
}
