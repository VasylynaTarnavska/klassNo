package kindgeek.school.klassno.controller;
import kindgeek.school.klassno.entity.dto.MarkDto;
import kindgeek.school.klassno.entity.request.LessonRequest;
import kindgeek.school.klassno.entity.request.MarkRequest;
import kindgeek.school.klassno.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mark")
public class MarkController {

    private final MarkService markService;

    @PostMapping
    public void create(@RequestBody MarkRequest markRequest){
        markService.create(markRequest);
    }

    @GetMapping ("/{id}")
    public MarkDto findDtoById(@PathVariable Long id){
        return markService.findDtoById(id);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id){markService.delete(id);}

    @GetMapping("/by-student/{studentId}")
    public List<MarkDto> findByStudentId (@PathVariable Long studentId){
        return markService.findByStudentId(studentId);
    }

    @GetMapping("/by-class-subject")
    public List<MarkDto> findBySubjectIdAndClassRoomId(@RequestParam Long subjectId, @RequestParam Long classRoomId){
        return markService.findBySubjectIdAndClassRoomId(subjectId, classRoomId);
    }

    @PutMapping("/edit/{id}")
    public void edit(@PathVariable Long id, @RequestBody MarkRequest markRequest){markService.edit(id,markRequest);}

}
