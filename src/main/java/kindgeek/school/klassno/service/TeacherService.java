package kindgeek.school.klassno.service;

import kindgeek.school.klassno.entity.Teacher;
import kindgeek.school.klassno.entity.dto.TeacherDto;
import kindgeek.school.klassno.entity.request.TeacherRequest;

public interface TeacherService {

    void create(TeacherRequest teacherRequest);

    TeacherDto findDtoById(Long id);

    Teacher findById(Long id);

    void delete(Long id);

    void edit(Long id, TeacherRequest teacherRequest);
}
