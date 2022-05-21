package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.dto.TeacherDto;
import kindgeek.school.klassno.entity.dto.criteria.TeacherCriteria;
import kindgeek.school.klassno.entity.request.TeacherRequest;
import kindgeek.school.klassno.service.AvatarStorageService;
import kindgeek.school.klassno.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;


    @PostMapping
    public void save(@RequestBody TeacherRequest teacherRequest) {
        log.info("Creating new teacher");
        teacherService.create(teacherRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public TeacherDto findDtoById(@PathVariable Long id) {
        log.info("Getting teacher by id: {}", id);
        return teacherService.findDtoById(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void delete(@PathVariable Long id) {
        log.info("Deleting teacher by id: {}", id);
        teacherService.delete(id);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void edit(@PathVariable Long id, @RequestBody TeacherRequest teacherRequest) {
        log.info("Editing teacher by id: {}", id);
        teacherService.edit(id, teacherRequest);
    }

    @GetMapping("/find")
    @PreAuthorize("hasAnyAuthority('TEACHER','STUDENT')")
    public Page<TeacherDto> find(TeacherCriteria teacherCriteria,
                                 @SortDefault(sort = "lastName", direction = Sort.Direction.ASC) Pageable page){
        log.info("Getting teachers by criteria");
        return teacherService.find(teacherCriteria, page);
    }
}
