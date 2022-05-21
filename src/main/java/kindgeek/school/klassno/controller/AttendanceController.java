package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.dto.AttendanceDto;
import kindgeek.school.klassno.entity.request.AttendanceRequest;
import kindgeek.school.klassno.service.AttendanceService;
import kindgeek.school.klassno.service.HomeworkFileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    private final HomeworkFileStorageService homeworkFileStorageService;

    @PostMapping
    @PreAuthorize("hasAuthority('TEACHER')")
    public void create(@RequestBody AttendanceRequest attendanceRequest){
        log.info("Creating new attendance");
        attendanceService.create(attendanceRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public AttendanceDto findDtoById(@PathVariable Long id){
        log.info("Getting attendance by id: {}", id);
        return attendanceService.findDtoById(id);
    }

    @PutMapping("/present/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void editPresent(@PathVariable Long id, @RequestParam Boolean isPresent){
        log.info("Editing student present by id: {}", id);
        attendanceService.editPresent(id, isPresent);
    }

    @GetMapping("/by-lesson/{lessonId}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public List<AttendanceDto> findByLessonId (@PathVariable Long lessonId){
        log.info("Getting attendance by lesson`s id: {}", lessonId);
        return attendanceService.findByLessonId(lessonId);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void delete(@PathVariable Long id){
        log.info("Deleting attendance by id: {}", id);
        attendanceService.delete(id);
    }

    @PostMapping("/{id}/add/files")
    @PreAuthorize("hasAuthority('STUDENT')")
    public void addFiles(@PathVariable Long id, @RequestParam("files") Set<MultipartFile> files){
        log.info("Adding homework files by attendance`s id: {}", id);
        homeworkFileStorageService.saveFiles(files, id);
    }

    @GetMapping("/{id}/load/files")
    @PreAuthorize("hasAuthority('TEACHER')")
    @ResponseBody
    public ResponseEntity<Resource> loadFileById(@PathVariable Long id){
        log.info("Loading homework files by attendance`s id: {}", id);
        Resource file = homeworkFileStorageService.loadFileById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\""
                        + file.getFilename()+ "\"").body(file);
    }

}
