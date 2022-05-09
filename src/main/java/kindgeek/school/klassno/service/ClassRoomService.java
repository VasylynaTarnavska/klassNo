package kindgeek.school.klassno.service;

import kindgeek.school.klassno.entity.ClassRoom;
import kindgeek.school.klassno.entity.dto.ClassRoomDto;
import kindgeek.school.klassno.entity.request.ClassRoomRequest;
public interface ClassRoomService {

    void create(ClassRoomRequest classRoomRequest);

    ClassRoomDto findDtoById(Long id);

    ClassRoom findById(Long id);

    void delete(Long id);
}
