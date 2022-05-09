package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.Subject;
import kindgeek.school.klassno.entity.dto.SubjectDto;
import kindgeek.school.klassno.entity.request.SubjectRequest;
import kindgeek.school.klassno.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public void save (@RequestBody SubjectRequest subjectRequest){
        subjectService.create(subjectRequest);
    }

    @GetMapping("/{id}")
    public SubjectDto findDtoById (@PathVariable Long id){
        return subjectService.findDtoById(id);
    }

    @GetMapping("/all")
    public List<SubjectDto> findAll(){
        return subjectService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public void delete (@PathVariable Long id){subjectService.delete(id);}

    @PutMapping("/edit-name/{id}")
    public void editName (@PathVariable Long id, @RequestParam String name){
        subjectService.editName(id, name);
    }

}
