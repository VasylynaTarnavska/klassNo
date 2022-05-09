package kindgeek.school.klassno.service.impl;


import kindgeek.school.klassno.entity.Subject;
import kindgeek.school.klassno.entity.dto.SubjectDto;
import kindgeek.school.klassno.entity.request.SubjectRequest;
import kindgeek.school.klassno.exception.NotFoundException;
import kindgeek.school.klassno.mapper.SubjectMapper;
import kindgeek.school.klassno.repository.SubjectRepository;
import kindgeek.school.klassno.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    private final SubjectMapper subjectMapper;

    @Override
    public void create(SubjectRequest subjectRequest) {
        Subject subject = createFromRequest(subjectRequest);
        subjectRepository.save(subject);
    }

    @Override
    public Subject findById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Subject with id:" + id + " does not exist"));
    }

    @Override
    public SubjectDto findDtoById(Long id) {
        Subject subject = findById(id);
        return subjectMapper.toDto(subject);
    }

    @Override
    public List<SubjectDto> findAll() {
            List<Subject>  subjects = subjectRepository.findAll();
            return subjects.stream().
                    map(subjectMapper::toDto)
                    .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id){
        subjectRepository.deleteById(id);
    }

    @Override
    public void editName(Long id, String name){
        Subject subject = findById(id);
        subject.setSubjectName(name);
        subjectRepository.save(subject);
    }

    private Subject createFromRequest(SubjectRequest subjectRequest){
        Subject subject = new Subject();
        subject.setSubjectName(subjectRequest.getSubjectName());
        return subject;
    }
}
