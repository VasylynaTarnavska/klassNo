package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.Message;
import kindgeek.school.klassno.entity.dto.MessageDto;
import kindgeek.school.klassno.entity.request.MessageRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message toEntity (MessageRequest messageRequest);

    MessageDto toDto (Message message);
}
