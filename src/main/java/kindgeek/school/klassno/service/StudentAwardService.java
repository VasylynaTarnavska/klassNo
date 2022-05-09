package kindgeek.school.klassno.service;

import kindgeek.school.klassno.entity.StudentAward;
import kindgeek.school.klassno.entity.dto.StudentAwardDto;
import kindgeek.school.klassno.entity.request.StudentAwardRequest;

import java.util.List;

public interface StudentAwardService {
    void create(StudentAwardRequest studentAwardRequest);

    StudentAwardDto findDtoById(Long id);

    StudentAward findById(Long id);

    List<StudentAwardDto> findByStudentId(Long studentId);

    void delete(Long id);

    void edit(Long id, StudentAwardRequest studentAwardRequest);
}
