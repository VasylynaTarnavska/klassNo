package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.dto.StudentAwardDto;
import kindgeek.school.klassno.entity.request.StudentAwardRequest;
import kindgeek.school.klassno.service.StudentAwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student-award")
public class StudentAwardController {

    private final StudentAwardService studentAwardService;

    @PostMapping
    public void create(@RequestBody StudentAwardRequest studentAwardRequest){
        studentAwardService.create(studentAwardRequest);
    }

    @GetMapping("/{id}")
    public StudentAwardDto findDtoById (@PathVariable Long id){
        return studentAwardService.findDtoById(id);
    }

    @GetMapping("/by-student/{studentId}")
    public List<StudentAwardDto> findByStudentId (@PathVariable Long studentId){
        return studentAwardService.findByStudentId(studentId);
    }

    @DeleteMapping("/delete/{id}")
    public  void delete(@PathVariable Long id){
        studentAwardService.delete(id);
    }

    @PutMapping("/edit/{id}")
    public void edit(@PathVariable Long id, @RequestBody StudentAwardRequest studentAwardRequest){
        studentAwardService.edit(id,studentAwardRequest);}
}
