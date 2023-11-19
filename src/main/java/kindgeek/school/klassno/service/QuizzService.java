package kindgeek.school.klassno.service;

import kindgeek.school.klassno.entity.Quizz;
import kindgeek.school.klassno.entity.dto.*;
import kindgeek.school.klassno.entity.request.QuestionRequest;
import kindgeek.school.klassno.entity.request.QuizzPassRequest;
import kindgeek.school.klassno.entity.request.QuizzRequest;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface QuizzService {

    Long createForLesson(QuizzRequest request);

    void addQuestion(QuestionRequest questionRequest);

    QuizzFullDto getDtoById(Long id);

    Quizz getById(Long id);

    void pass(QuizzPassRequest passRequest);

    List<QuizzTeacherListDto> getListForTeacher();

    List<QuizzStudentListDto> getListForStudent(Long studentId);

    List<QuizzResultDto> getQuizzResultsById(Long quizzId);

    QuizzResultFullDto getQuizzResultForStudentById(Long studentId, Long quizzId);
}
