package kindgeek.school.klassno.controller;
import kindgeek.school.klassno.entity.dto.StudentMarksDto;
import kindgeek.school.klassno.entity.dto.StudentDto;
import kindgeek.school.klassno.entity.request.StudentRequest;
import kindgeek.school.klassno.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private  final StudentService studentService;

    @PostMapping
    @PreAuthorize("hasAuthority('TEACHER')")
    public Long save(@RequestBody StudentRequest studentRequest){
        log.info("Creating new student");
        return studentService.create(studentRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public StudentDto findDtoById(@PathVariable Long id){
        log.info("Getting student by id: {}", id);
        return studentService.findDtoById(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void delete (@PathVariable Long id){
        log.info("Deleting student by id: {}", id);
        studentService.delete(id);}

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public Long edit (@PathVariable Long id, @RequestBody StudentRequest studentRequest){
        log.info("Editing student by id: {}", id);
        studentService.edit(id, studentRequest);
        return id;
    }

    @GetMapping("/by-class/{classRoomId}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public Page<StudentDto> findByClassRoomId(@PathVariable Long classRoomId,
                                              @SortDefault(sort = "lastName", direction = Sort.Direction.ASC) Pageable page){
        log.info("Getting students by criteria");
        return studentService.findByClassRoomId(classRoomId, page);
    }

    @GetMapping("/scorecards/{classId}/{subjectId}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public List<StudentMarksDto> findScorecards (@PathVariable Long classId, @PathVariable Long subjectId ){
        log.info("Getting scorecards by class id: {} and subject id: {}", classId, subjectId);
        return studentService.getScorecards(classId, subjectId);
    }
}
