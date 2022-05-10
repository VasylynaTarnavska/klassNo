package kindgeek.school.klassno.service;

import kindgeek.school.klassno.entity.Teacher;
import kindgeek.school.klassno.entity.dto.TeacherDto;
import kindgeek.school.klassno.entity.dto.criteria.TeacherCriteria;
import kindgeek.school.klassno.entity.request.TeacherRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherService {

    void create(TeacherRequest teacherRequest);

    TeacherDto findDtoById(Long id);

    Teacher findById(Long id);

    void delete(Long id);

    void edit(Long id, TeacherRequest teacherRequest);

    Page<TeacherDto> find (TeacherCriteria teacherCriteria, Pageable page);
}
