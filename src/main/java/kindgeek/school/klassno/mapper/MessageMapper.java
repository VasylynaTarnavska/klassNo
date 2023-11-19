package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.Message;
import kindgeek.school.klassno.entity.dto.MessageDto;
import kindgeek.school.klassno.entity.request.MessageRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ChatMapper.class})
public interface MessageMapper {

    @Mapping(target = "sender", source = "senderId")
    @Mapping(target = "chat", source = "chatId")
    Message toEntity (MessageRequest messageRequest);

    MessageDto toDto (Message message);
}
