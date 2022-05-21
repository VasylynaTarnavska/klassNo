package kindgeek.school.klassno.controller;
import kindgeek.school.klassno.entity.dto.MarkDto;
import kindgeek.school.klassno.entity.request.LessonRequest;
import kindgeek.school.klassno.entity.request.MarkRequest;
import kindgeek.school.klassno.service.MarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mark")
public class MarkController {

    private final MarkService markService;

    @PostMapping
    @PreAuthorize("hasAuthority('TEACHER')")
    public void create(@RequestBody MarkRequest markRequest){
        log.info("Creating new mark");
        markService.create(markRequest);
    }

    @GetMapping ("/{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public MarkDto findDtoById(@PathVariable Long id){
        log.info("Getting mark by id: {}", id);
        return markService.findDtoById(id);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void delete(@PathVariable Long id){
        log.info("Deleting mark by id: {}", id);
        markService.delete(id);}

    @GetMapping("/by-student/{studentId}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public List<MarkDto> findByStudentId (@PathVariable Long studentId){
        log.info("Getting marks by student`s id: {}", studentId);
        return markService.findByStudentId(studentId);
    }

    @GetMapping("/by-class-subject")
    @PreAuthorize("hasAuthority('TEACHER')")
    public List<MarkDto> findBySubjectIdAndClassRoomId(@RequestParam Long subjectId, @RequestParam Long classRoomId){
        log.info("Getting marks by subject`s id: {} and class room id: {}", subjectId, classRoomId);
        return markService.findBySubjectIdAndClassRoomId(subjectId, classRoomId);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void edit(@PathVariable Long id, @RequestBody MarkRequest markRequest){
        log.info("Editing marks by id: {}", id);
        markService.edit(id,markRequest);}

}
