package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.Teacher;
import kindgeek.school.klassno.entity.dto.TeacherDto;
import kindgeek.school.klassno.entity.request.TeacherRequest;
import kindgeek.school.klassno.service.FilesStorageService;
import kindgeek.school.klassno.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    private final FilesStorageService filesStorageService;

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

    @PostMapping("/{id}/add/img")
    public void addAvatar(@PathVariable Long id, @RequestParam("avatar")MultipartFile avatar){
        filesStorageService.saveAvatar(avatar, id);
    }

    @GetMapping("/{id}/img")
    public ResponseEntity<Resource> getAvatar(@PathVariable Long id){
        Resource avatar = filesStorageService.loadByTeacherId(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + avatar.getFilename()+ "\"")
                .body(avatar);
    }
//todo add find methods
}
