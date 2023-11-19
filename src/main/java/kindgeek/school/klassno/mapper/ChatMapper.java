package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.Chat;
import kindgeek.school.klassno.entity.dto.ChatDto;
import kindgeek.school.klassno.entity.request.ChatRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TeacherMapper.class, StudentMapper.class})
public interface ChatMapper {

    @Mapping(target = "teacher", source = "teacherId")
    @Mapping(target = "student", source = "studentId")
    Chat toEntity (ChatRequest chatRequest);

    ChatDto toDto (Chat chat);

    default Chat fromId(Long id){
        if (id == null) {
            return null;
        }

        Chat chat= new Chat();
        chat.setId(id);
        return chat;
    }
}
