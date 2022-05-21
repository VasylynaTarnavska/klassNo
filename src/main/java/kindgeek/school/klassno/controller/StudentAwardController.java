package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.dto.StudentAwardDto;
import kindgeek.school.klassno.entity.request.StudentAwardRequest;
import kindgeek.school.klassno.service.StudentAwardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/student-award")
public class StudentAwardController {

    private final StudentAwardService studentAwardService;

    @PostMapping
    @PreAuthorize("hasAuthority('TEACHER')")
    public void create(@RequestBody StudentAwardRequest studentAwardRequest){
        log.info("Creating new student`s award");
        studentAwardService.create(studentAwardRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public StudentAwardDto findDtoById (@PathVariable Long id){
        log.info("Getting student`s award by id: {}", id);
        return studentAwardService.findDtoById(id);
    }

    @GetMapping("/by-student/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public List<StudentAwardDto> findByStudentId (@PathVariable Long studentId){
        log.info("Getting student`s award by student`s id: {}", studentId);
        return studentAwardService.findByStudentId(studentId);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public  void delete(@PathVariable Long id){
        log.info("Deleting student`s award by id: {}", id);
        studentAwardService.delete(id);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void edit(@PathVariable Long id, @RequestBody StudentAwardRequest studentAwardRequest){
        log.info("Editing student`s award by id: {}", id);
        studentAwardService.edit(id,studentAwardRequest);}
}
