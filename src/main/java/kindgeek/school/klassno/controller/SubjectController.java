package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.dto.SubjectDto;
import kindgeek.school.klassno.entity.request.SubjectRequest;
import kindgeek.school.klassno.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    @PreAuthorize("hasAuthority('TEACHER')")
    public void save (@RequestBody SubjectRequest subjectRequest){
        log.info("Creating new subject");
        subjectService.create(subjectRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public SubjectDto findDtoById (@PathVariable Long id){
        log.info("Getting subject by id: {}", id);
        return subjectService.findDtoById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('TEACHER')")
    public List<SubjectDto> findAll(){
        log.info("Getting all subject");
        return subjectService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void delete (@PathVariable Long id){
        log.info("Deleting subject by id: {}", id);
        subjectService.delete(id);}

    @PutMapping("/edit-name/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void editName (@PathVariable Long id, @RequestParam String name){
        log.info("Editing subject`s name by id: {}", id);
        subjectService.editName(id, name);
    }

}
