package kindgeek.school.klassno.service;

import kindgeek.school.klassno.entity.Subject;
import kindgeek.school.klassno.entity.dto.SubjectDto;
import kindgeek.school.klassno.entity.request.SubjectRequest;

import java.util.List;

public interface SubjectService {

    void create(SubjectRequest subjectRequest);

    Subject findById(Long id);

    SubjectDto findDtoById(Long id);

    List<SubjectDto> findAll();

    void delete(Long id);

    void editName(Long id, String name);
}
