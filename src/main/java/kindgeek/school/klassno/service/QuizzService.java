package kindgeek.school.klassno.service;

import kindgeek.school.klassno.entity.Quizz;
import kindgeek.school.klassno.entity.dto.*;
import kindgeek.school.klassno.entity.request.QuestionRequest;
import kindgeek.school.klassno.entity.request.QuizzPassRequest;
import kindgeek.school.klassno.entity.request.QuizzRequest;

import java.util.List;

public interface QuizzService {

    Long createForLesson(QuizzRequest request);

    void delete(Long id);

    void addQuestion(QuestionRequest questionRequest);

    QuizzFullDto getDtoById(Long id);

    List<QuestionDto> getQuestionsByQuizzId(Long quizzId);

    Quizz getById(Long id);

    void pass(QuizzPassRequest passRequest);

    List<QuizzTeacherListDto> getListForTeacher();

    List<QuizzStudentListDto> getListForStudent(Long studentId);

    List<QuizzResultDto> getQuizzResultsById(Long quizzId);

    QuizzResultFullDto getQuizzResultForStudentById(Long studentId, Long quizzId);
}
