package kindgeek.school.klassno.controller;
import kindgeek.school.klassno.entity.Student;
import kindgeek.school.klassno.entity.dto.StudentDto;
import kindgeek.school.klassno.entity.request.StudentRequest;
import kindgeek.school.klassno.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private  final StudentService studentService;

    @PostMapping
    public void save(@RequestBody StudentRequest studentRequest){
        studentService.create(studentRequest);
    }

    @GetMapping("/{id}")
    public StudentDto findDtoById(@PathVariable Long id){
        return studentService.findDtoById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete (@PathVariable Long id){studentService.delete(id);}

    @PutMapping("/edit/{id}")
    public void edit (@PathVariable Long id, @RequestBody StudentRequest studentRequest){
        studentService.edit(id, studentRequest);
    }

    @GetMapping("/by-class/{classRoomId}")
    public Page<StudentDto> findByClassRoomId(@PathVariable Long classRoomId,
                                              @SortDefault(sort = "lastName", direction = Sort.Direction.ASC) Pageable page){
        return studentService.findByClassRoomId(classRoomId, page);
    }
}
