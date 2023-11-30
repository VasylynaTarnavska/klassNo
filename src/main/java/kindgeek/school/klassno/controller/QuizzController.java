package kindgeek.school.klassno.controller;

import kindgeek.school.klassno.entity.dto.*;
import kindgeek.school.klassno.entity.request.QuestionRequest;
import kindgeek.school.klassno.entity.request.QuizzPassRequest;
import kindgeek.school.klassno.entity.request.QuizzRequest;
import kindgeek.school.klassno.service.QuizzService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/quizz")
public class QuizzController {

    private final QuizzService quizzService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public Long create(@RequestBody @Valid QuizzRequest request) {
        log.info("Creating new quizz for lesson with id: {}", request.getLessonId());
        return quizzService.createForLesson(request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public void delete (@PathVariable Long id) {
        log.info("Deleting  quizz with id: {}", id);
        quizzService.delete(id);
    }

    @PostMapping("/add-question")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public void addQuestion(@RequestBody @Valid QuestionRequest request) {
        log.info("Add question for quizz with id: {}", request.getQuizzId());
        quizzService.addQuestion(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public QuizzFullDto getById(@PathVariable Long id) {
        log.info("Getting quizz by id: {}", id);
        return quizzService.getDtoById(id);
    }

    @GetMapping("questions/{quizzId}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public List<QuestionDto> getQuestionsByQuizzId(@PathVariable Long quizzId){
        log.info("Getting questions by id: {}", quizzId);
        return quizzService.getQuestionsByQuizzId(quizzId);
    }

    @PostMapping("/pass")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public void pass(@RequestBody @Valid QuizzPassRequest request) {
        log.info("Pass quizz with id: {}", request.getQuizzId());
        quizzService.pass(request);
    }

    @GetMapping("/list/for-teacher")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public List<QuizzTeacherListDto> getListForTeacher() {
        log.info("Getting quizzes list for teacher");
        return quizzService.getListForTeacher();
    }

    @GetMapping("/list/for-student/{studentId}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER')")
    public List<QuizzStudentListDto> getListForStudent(@PathVariable Long studentId) {
        log.info("Getting quizzes list for student: {}", studentId);
        return quizzService.getListForStudent(studentId);
    }

    @GetMapping("/result/{quizzId}")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public List<QuizzResultDto> getQuizzResultsById(@PathVariable Long quizzId) {
        log.info("Getting quizz results by quizz id: {}", quizzId);
        return quizzService.getQuizzResultsById(quizzId);
    }

    @GetMapping("/result/{studentId}/{quizzId}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public QuizzResultFullDto getQuizzResultForStudentById(@PathVariable Long quizzId, @PathVariable Long studentId) {
        log.info("Getting quizz result for student: {} by quizz id: {}", studentId, quizzId);
        return quizzService.getQuizzResultForStudentById(studentId, quizzId);
    }


}
