package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Mark;
import kindgeek.school.klassno.entity.StudentAward;
import kindgeek.school.klassno.entity.dto.StudentAwardDto;
import kindgeek.school.klassno.entity.request.MarkRequest;
import kindgeek.school.klassno.entity.request.StudentAwardRequest;
import kindgeek.school.klassno.exception.NotFoundException;
import kindgeek.school.klassno.mapper.StudentAwardMapper;
import kindgeek.school.klassno.repository.StudentAwardRepository;
import kindgeek.school.klassno.service.StudentAwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentAwardServiceImpl implements StudentAwardService {

    private final StudentAwardRepository studentAwardRepository;

    private final StudentAwardMapper studentAwardMapper;

    @Override
    public void create(StudentAwardRequest studentAwardRequest) {
        StudentAward studentAward = studentAwardMapper.toEntity(studentAwardRequest);
        studentAwardRepository.save(studentAward);
    }

    @Override
    public StudentAwardDto findDtoById(Long id) {
        StudentAward studentAward = studentAwardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("StudentAward with id:" + id + " does not exist"));
        return studentAwardMapper.toDto(studentAward);
    }

    @Override
    public StudentAward findById(Long id) {
        return studentAwardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("StudentAward with id:" + id + " does not exist"));
    }

    @Override
    public List<StudentAwardDto> findByStudentId(Long studentId) {
        List<StudentAward> studentAwards = studentAwardRepository.findByStudentIdOrderByDate(studentId);
        return studentAwards.stream()
                .map(studentAwardMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id){
        studentAwardRepository.deleteById(id);
    }

    @Override
    public void edit(Long id, StudentAwardRequest studentAwardRequest){
        StudentAward studentAward = findById(id);
        studentAwardMapper.update(studentAward, studentAwardRequest);
        studentAwardRepository.save(studentAward);
    }



}
