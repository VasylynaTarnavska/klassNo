package kindgeek.school.klassno.entity.request;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class MessageRequest {
    private Long id;

    @NotBlank
    private String messageText;

    @NotNull
    private Long senderId;

    @NotNull
    private Long chatId;
}
