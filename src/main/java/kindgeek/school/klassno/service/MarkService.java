package kindgeek.school.klassno.service;

import kindgeek.school.klassno.entity.Mark;
import kindgeek.school.klassno.entity.dto.MarkDto;
import kindgeek.school.klassno.entity.request.MarkRequest;

import java.util.List;

public interface MarkService {
    void create(MarkRequest markRequest);

    MarkDto findDtoById(Long id);

    Mark findById(Long id);

    void delete(Long id);

    List<MarkDto> findByStudentId(Long studentId);

    List<MarkDto> findBySubjectIdAndClassRoomId(Long subjectId, Long classRoomId);

    void edit(Long id, MarkRequest markRequest);
}
