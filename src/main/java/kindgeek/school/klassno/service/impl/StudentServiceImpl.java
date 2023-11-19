package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.ClassRoom;
import kindgeek.school.klassno.entity.Student;
import kindgeek.school.klassno.entity.dto.StudentDto;
import kindgeek.school.klassno.entity.dto.StudentMarksDto;
import kindgeek.school.klassno.entity.request.StudentRequest;
import kindgeek.school.klassno.enums.UserRole;
import kindgeek.school.klassno.exception.NotFoundException;
import kindgeek.school.klassno.mapper.AttendanceMapper;
import kindgeek.school.klassno.mapper.StudentMapper;
import kindgeek.school.klassno.repository.StudentRepository;
import kindgeek.school.klassno.service.ClassRoomService;
import kindgeek.school.klassno.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;
    private final ClassRoomService classRoomService;
    private final AttendanceMapper attendanceMapper;

    @Override
    public Long create(StudentRequest studentRequest) {
        Student student = studentMapper.toEntity(studentRequest);
        student.setLogin(generateLogin(student));
        student.setRole(UserRole.STUDENT);
        student.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        studentRepository.save(student);
        return student.getId();
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student with id:" + id + " does not exist"));
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
    public void edit(Long id, StudentRequest studentRequest) {
        Student student = findById(id);
        studentMapper.update(student, studentRequest);
        studentRepository.save(student);
    }

    @Override
    public Page<StudentDto> findByClassRoomId(Long classRoomId, Pageable page) {
        Page<Student> students = studentRepository.findByClassRoomId(classRoomId, page);
        return students.map(studentMapper::toDto);
    }

    @Override
    public List<Student> findByClassRoomId(Long classRoomId) {
        return studentRepository.findByClassRoomId(classRoomId);
    }


    @Override
    public List<StudentMarksDto> getScorecards(Long classId, Long subjectId) {
        return findByClassRoomId(classId).stream()
                .peek(student -> student.setAttendancesBySubjectId(subjectId))
                .map(this::toStudentMarkDto)
                .sorted(Comparator.comparing(StudentMarksDto::getLastName))
                .collect(Collectors.toList());
    }


    private String generateLogin(Student student) {
        ClassRoom classRoom = classRoomService.findById(student.getClassRoom().getId());
        return student.getFirstName() +
                student.getLastName() +
                classRoom.getClassName().trim().toLowerCase().replaceAll("-", "") +
                "@klassno";
    }

    private StudentMarksDto toStudentMarkDto(Student student) {
        return StudentMarksDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .attendances(attendanceMapper.toShortDtoList(student.getAttendances()))
                .build();
    }

}
