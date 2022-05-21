package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Mark;
import kindgeek.school.klassno.entity.Student;
import kindgeek.school.klassno.entity.dto.StudentDto;
import kindgeek.school.klassno.entity.request.MarkRequest;
import kindgeek.school.klassno.entity.request.StudentRequest;
import kindgeek.school.klassno.enums.UserRole;
import kindgeek.school.klassno.exception.NotFoundException;
import kindgeek.school.klassno.mapper.StudentMapper;
import kindgeek.school.klassno.repository.StudentRepository;
import kindgeek.school.klassno.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    @Override
    public void create(StudentRequest studentRequest) {
        Student student = studentMapper.toEntity(studentRequest);
        student.setRole(UserRole.STUDENT);
        studentRepository.save(student);
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Student with id:" + id + " does not exist"));
    }

    @Override
    public StudentDto findDtoById(Long id) {
        Student student = findById(id);
        return studentMapper.toDto(student);
    }

    @Override
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void edit(Long id, StudentRequest studentRequest){
        Student student= findById(id);
        studentMapper.update(student, studentRequest);
        studentRepository.save(student);
    }

    @Override
    public Page<StudentDto> findByClassRoomId(Long classRoomId, Pageable page){
        Page<Student> students = studentRepository.findByClassRoomId(classRoomId, page);
        return students.map(studentMapper::toDto);
    }


}
