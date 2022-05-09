package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.dto.LessonDto;
import kindgeek.school.klassno.entity.dto.criteria.LessonCriteria;
import kindgeek.school.klassno.entity.request.LessonRequest;
import kindgeek.school.klassno.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lesson")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/{id}")
    private LessonDto findById(@PathVariable Long id){
        return lessonService.findDtoById(id);
    }

    @PostMapping
    public void create(@RequestBody LessonRequest lessonRequest){
        lessonService.create(lessonRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){lessonService.delete(id);}

    @PutMapping("/edit/{id}")
    public void edit(@PathVariable Long id, @RequestBody LessonRequest lessonRequest){lessonService.edit(id,lessonRequest);}

    @GetMapping("/find")
    public Page<LessonDto> find(LessonCriteria lessonCriteria,
                                @SortDefault(sort = "lessonTime", direction = Sort.Direction.ASC) Pageable page){
        return lessonService.find(lessonCriteria, page);
    }
}
