package kindgeek.school.klassno.service;

import kindgeek.school.klassno.entity.Student;
import kindgeek.school.klassno.entity.dto.StudentDto;
import kindgeek.school.klassno.entity.request.StudentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    Long create(StudentRequest studentRequest);

    Student findById(Long id);

    StudentDto findDtoById(Long id);

    void delete(Long id);

    void edit(Long id, StudentRequest studentRequest);

    Page<StudentDto> findByClassRoomId(Long classRoomId, Pageable page);

    List<Student> findStudentByClassRoomId(Long classRoomId);
}
