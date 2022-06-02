package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.Chat;
import kindgeek.school.klassno.entity.dto.ChatDto;
import kindgeek.school.klassno.entity.request.ChatRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    Chat toEntity (ChatRequest chatRequest);

    ChatDto toDto (Chat chat);
}
