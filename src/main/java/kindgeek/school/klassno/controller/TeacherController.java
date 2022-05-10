package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.dto.TeacherDto;
import kindgeek.school.klassno.entity.dto.criteria.TeacherCriteria;
import kindgeek.school.klassno.entity.request.TeacherRequest;
import kindgeek.school.klassno.service.AvatarStorageService;
import kindgeek.school.klassno.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;


    @PostMapping
    public void save(@RequestBody TeacherRequest teacherRequest) {

        teacherService.create(teacherRequest);
    }

    @GetMapping("/{id}")
    public TeacherDto findDtoById(@PathVariable Long id) {
        return teacherService.findDtoById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        teacherService.delete(id);
    }

    @PutMapping("/edit/{id}")
    public void edit(@PathVariable Long id, @RequestBody TeacherRequest teacherRequest) {
        teacherService.edit(id, teacherRequest);
    }

    @GetMapping("/find")
    public Page<TeacherDto> find(TeacherCriteria teacherCriteria,
                                 @SortDefault(sort = "lastName", direction = Sort.Direction.ASC) Pageable page){
        return teacherService.find(teacherCriteria, page);
    }
}
