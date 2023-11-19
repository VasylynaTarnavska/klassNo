package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.Quizz;
import kindgeek.school.klassno.entity.QuizzResult;
import kindgeek.school.klassno.entity.dto.QuizzFullDto;
import kindgeek.school.klassno.entity.dto.QuizzStudentListDto;
import kindgeek.school.klassno.entity.dto.QuizzTeacherListDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class, LessonMapper.class})
public interface QuizzMapper {

    QuizzFullDto toDto(Quizz quizz);

    QuizzTeacherListDto toQuizzTeacherListDto(Quizz quizz);

    QuizzStudentListDto toQuizzStudentListDto(Quizz quizz, Long studentId);


    default Quizz fromId(Long id) {
        if (id == null) {
            return null;
        }

        Quizz quizz = new Quizz();
        quizz.setId(id);
        return quizz;
    }

    @AfterMapping
    default void afterToQuizzTeacherListDto(@MappingTarget QuizzTeacherListDto dto, Quizz quizz) {
        dto.setNumberOfStudentInClass(quizz.getLesson().getClassRoom().getStudents().size());
        dto.setNumberOfResults(quizz.getQuizzResults().size());
        dto.setResult(quizz.getQuizzResults().stream()
                .map(QuizzResult::getResult)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(quizz.getQuizzResults().size()))
                .setScale(0, RoundingMode.HALF_UP));
    }

    @AfterMapping
    default void afterToQuizzStudentListDto(@MappingTarget QuizzStudentListDto dto, Quizz quizz, Long studentId) {
        QuizzResult studentResult = quizz.getQuizzResults().stream()
                .filter(result -> Objects.equals(studentId, result.getAttendance().getStudent().getId()))
                .findAny()
                .orElse(null);
        dto.setResult(Objects.nonNull(studentResult)
                ? studentResult.getResult()
                : null);
        dto.setCorrectAnswersAmount(Objects.nonNull(studentResult)
                ? studentResult.getCorrectAnswersAmount()
                : null);
    }
}
