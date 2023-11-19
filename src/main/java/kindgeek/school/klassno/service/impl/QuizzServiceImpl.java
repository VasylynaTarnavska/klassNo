package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.*;
import kindgeek.school.klassno.entity.dto.*;
import kindgeek.school.klassno.entity.request.QuestionPassRequest;
import kindgeek.school.klassno.entity.request.QuestionRequest;
import kindgeek.school.klassno.entity.request.QuizzPassRequest;
import kindgeek.school.klassno.entity.request.QuizzRequest;
import kindgeek.school.klassno.exception.NotFoundException;
import kindgeek.school.klassno.mapper.QuizzMapper;
import kindgeek.school.klassno.mapper.QuizzResultMapper;
import kindgeek.school.klassno.repository.QuizzRepository;
import kindgeek.school.klassno.repository.QuizzResultRepository;
import kindgeek.school.klassno.service.AttendanceService;
import kindgeek.school.klassno.service.LessonService;
import kindgeek.school.klassno.service.QuestionService;
import kindgeek.school.klassno.service.QuizzService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizzServiceImpl implements QuizzService {

    private final QuizzRepository repository;
    private final LessonService lessonService;
    private final QuestionService questionService;
    private final QuizzMapper mapper;
    private final AttendanceService attendanceService;
    private final QuizzResultRepository quizzResultRepository;
    private final QuizzResultMapper quizzResultMapper;

    @Override
    public Long createForLesson(QuizzRequest request) {
        Lesson lesson = lessonService.getById(request.getLessonId());
        Quizz quizz = Quizz.builder()
                .name(request.getName())
                .lesson(lesson)
                .build();
        quizz = repository.save(quizz);
        lesson.setQuizz(quizz);
        lessonService.save(lesson);
        return quizz.getId();
    }

    @Override
    public void addQuestion(QuestionRequest questionRequest) {
        Quizz quizz = getById(questionRequest.getQuizzId());
        quizz.getQuestions().add(questionService.create(questionRequest));
        quizz.setNumberOfQuestions(quizz.getQuestions().size());
        repository.save(quizz);
    }

    @Override
    public QuizzFullDto getDtoById(Long id) {
        Quizz quizz = getById(id);
        return mapper.toFullDto(quizz);
    }

    @Override
    public Quizz getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Quizz with id:" + id + "not found"));
    }

    @Override
    public void pass(QuizzPassRequest passRequest) {
        Quizz quizz = getById(passRequest.getQuizzId());
        Attendance attendance = attendanceService.getByStudentIdAndQuizzId(passRequest.getStudentId(), passRequest.getQuizzId());
        QuizzResult quizzResult = new QuizzResult();
        attendance.setQuizzResult(quizzResult);
        quizzResult.setQuizz(quizz);
        quizzResult.setAttendance(attendance);

        List<QuestionResult> results = passRequest.getQuestionPassRequests()
                .stream()
                .map(request -> createQuestionResult(request, quizzResult))
                .toList();
        quizzResult.setQuestionResults(results);

        List<QuestionResult> correctResults = results.stream()
                .filter(QuestionResult::getIsCorrect)
                .toList();
        quizzResult.setCorrectAnswersAmount(correctResults.size());
        quizzResult.setResult(BigDecimal.valueOf(quizzResult.getCorrectAnswersAmount())
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(quizz.getNumberOfQuestions()))
                .stripTrailingZeros()
                .setScale(0, RoundingMode.HALF_UP));

        quizzResultRepository.save(quizzResult);
    }

    @Override
    public List<QuizzTeacherListDto> getListForTeacher() {
        List<Quizz> quizzes = repository.findAll();
        return quizzes.stream()
                .map(mapper::toQuizzTeacherListDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuizzStudentListDto> getListForStudent(Long studentId) {
        List<Quizz> quizzes = repository.findByStudentId(studentId);
        return quizzes.stream()
                .map(quizz -> mapper.toQuizzStudentListDto(quizz, studentId))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuizzResultDto> getQuizzResultsById(Long quizzId) {
        Quizz quizz = getById(quizzId);
        return quizz.getQuizzResults().stream()
                .map(quizzResultMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuizzResultFullDto getQuizzResultForStudentById(Long studentId, Long quizzId) {
        Quizz quizz = getById(quizzId);
        QuizzResult studentResult = quizz.getQuizzResults().stream()
                .filter(quizzResult -> Objects.equals(studentId, quizzResult.getAttendance().getStudent().getId()))
                .findFirst()
                .orElse(null);
        if (Objects.isNull(studentResult)){
            return null;
        }
        studentResult.getQuizz().getQuestions().forEach(question -> evaluateStudentQuestionResult(question, studentId));
        return quizzResultMapper.toFullDto(studentResult);
    }

    private void evaluateStudentQuestionResult (Question question, Long studentId) {
        QuestionResult studentResult = question.getResults().stream()
                .filter(result -> Objects
                        .equals(studentId, result.getQuizzResult().getAttendance().getStudent().getId()))
                .findFirst()
                .orElse(null);
        question.setStudentResult(studentResult);
    }

    private QuestionResult createQuestionResult(QuestionPassRequest request, QuizzResult result) {
        Question question = questionService.getById(request.getQuestionId());
        Optional<Answer> selectedAnswer = question.getAnswers().stream()
                .filter(answer -> Objects.equals(answer.getId(), request.getSelectedAnswerId()))
                .findFirst();
        Boolean isCorrect = selectedAnswer.isPresent()
                ? selectedAnswer.get().getIsCorrect()
                : false;

        return QuestionResult.builder()
                .question(question)
                .selectedAnswer(selectedAnswer.orElse(null))
                .isCorrect(isCorrect)
                .quizzResult(result)
                .build();
    }
}

