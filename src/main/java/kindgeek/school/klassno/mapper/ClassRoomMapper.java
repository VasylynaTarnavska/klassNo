package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.ClassRoom;
import kindgeek.school.klassno.entity.dto.ClassRoomDto;
import kindgeek.school.klassno.entity.request.ClassRoomRequest;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface ClassRoomMapper {

    ClassRoom toEntity (ClassRoomRequest classRoomRequest);

    ClassRoomDto toDto (ClassRoom classRoom);

    default ClassRoom fromId(Long id){
        if (id == null) {
            return null;
        }

        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(id);
        return classRoom;
    }
}
