package kindgeek.school.klassno.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDto {

    private Long id;

    private String messageText;

    private LocalDateTime messageTime;

    private Long senderId;

    private ChatDto chat;

}
