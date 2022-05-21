package kindgeek.school.klassno.entity.dto;

import kindgeek.school.klassno.entity.Chat;
import kindgeek.school.klassno.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class MessageDto {

    private Long id;

    private String messageText;

    private LocalDate messageTime;

    private Long senderId;

    private ChatDto chat;

}
