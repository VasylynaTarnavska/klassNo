package kindgeek.school.klassno.service;

import kindgeek.school.klassno.entity.ClassRoom;
import kindgeek.school.klassno.entity.dto.ClassRoomDto;
import kindgeek.school.klassno.entity.request.ClassRoomRequest;
import kindgeek.school.klassno.enums.Grade;

import java.util.List;

public interface ClassRoomService {

    Long create(ClassRoomRequest classRoomRequest);

    List<ClassRoomDto> findAll();

    List<Grade> getAll();

    ClassRoomDto findDtoById(Long id);

    ClassRoom findById(Long id);

    void delete(Long id);
}
