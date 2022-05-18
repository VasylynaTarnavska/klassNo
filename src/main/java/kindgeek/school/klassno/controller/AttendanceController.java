package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.dto.AttendanceDto;
import kindgeek.school.klassno.entity.request.AttendanceRequest;
import kindgeek.school.klassno.service.AttendanceService;
import kindgeek.school.klassno.service.HomeworkFileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    private final HomeworkFileStorageService homeworkFileStorageService;

    @PostMapping
    public void create(@RequestBody AttendanceRequest attendanceRequest){

        attendanceService.create(attendanceRequest);
    }

    @GetMapping("/{id}")
    public AttendanceDto findDtoById(@PathVariable Long id){
        return attendanceService.findDtoById(id);
    }

    @PutMapping("/present/{id}")
    public void editPresent(@PathVariable Long id, @RequestParam Boolean isPresent){
        attendanceService.editPresent(id, isPresent);
    }

    @GetMapping("/by-lesson/{lessonId}")
    public List<AttendanceDto> findByLessonId (@PathVariable Long lessonId){
        return attendanceService.findByLessonId(lessonId);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        attendanceService.delete(id);
    }

    @PostMapping("/{id}/add/files")
    public void addFiles(@PathVariable Long id, @RequestParam("files") Set<MultipartFile> files){
        homeworkFileStorageService.saveFiles(files, id);
    }

    @GetMapping("/{id}/load/files")
    @ResponseBody
    public ResponseEntity<Resource> loadFileById(@PathVariable Long id){
        Resource file = homeworkFileStorageService.loadFileById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\""
                        + file.getFilename()+ "\"").body(file);
    }

//todo add works with files
}
