package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.dto.LessonDto;
import kindgeek.school.klassno.entity.dto.LessonShortDto;
import kindgeek.school.klassno.entity.dto.criteria.LessonCriteria;
import kindgeek.school.klassno.entity.request.LessonRequest;
import kindgeek.school.klassno.service.LessonFilesStorageService;
import kindgeek.school.klassno.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/lesson")
public class LessonController {

    private final LessonService lessonService;

    private final LessonFilesStorageService lessonFilesStorageService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    private LessonDto findById(@PathVariable Long id) {
        log.info("Getting lesson by id: {}", id);
        return lessonService.getDtoById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('TEACHER')")
    public Long create(@ModelAttribute LessonRequest lessonRequest) {
        log.info("Creating new lesson");
        log.info("Creating new lesson");
        return lessonService.create(lessonRequest);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void deleteById(@PathVariable Long id) {
        log.info("Deleting lesson by id: {}", id);
        lessonService.delete(id);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void edit(@PathVariable Long id, @RequestBody LessonRequest lessonRequest) {
        log.info("Editing lesson by id: {}", id);
        lessonService.edit(id, lessonRequest);
    }

    @GetMapping("/find")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public Page<LessonDto> find(LessonCriteria lessonCriteria,
                                @SortDefault(sort = "lessonTime", direction = Sort.Direction.ASC) Pageable page) {
        log.info("Getting lessons by criteria");
        return lessonService.find(lessonCriteria, page);
    }

    @GetMapping("/without-quiz")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public List<LessonDto> getByTeacherIdWithoutQuiz(Long teacherId) {
        log.info("Getting lessons by teacher id without quizz");
        return lessonService.getByTeacherIdWithoutQuiz(teacherId);
    }


    @GetMapping("/find/{studentId}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public List<LessonDto> findLessonByStudentId(@PathVariable Long studentId) {
        log.info("Getting lessons by studentId");
        return lessonService.getByStudentId(studentId);
    }

    @PostMapping("/{id}/add/files")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void addFiles(@PathVariable Long id, @RequestParam("files") Set<MultipartFile> files) {
        log.info("Adding files by id: {}", id);
        lessonFilesStorageService.saveFiles(files, id);
    }

    @GetMapping("/{id}/load/files")
    @PreAuthorize("hasAuthority('STUDENT')")
    @ResponseBody
    public ResponseEntity<Resource> loadFileById(@PathVariable Long id) {
        log.info("Loading files by id: {}", id);
        Resource file = lessonFilesStorageService.loadFileById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/short/{classId}/{subjectId}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public List<LessonShortDto> findShortInfo(@PathVariable Long classId, @PathVariable Long subjectId) {
        log.info("Getting shortInfo by class id: {} and subject id: {}", classId, subjectId);
        return lessonService.getShortInfo(classId, subjectId);
    }
}
