package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.QuizzResult;
import kindgeek.school.klassno.entity.dto.QuizzResultDto;
import kindgeek.school.klassno.entity.dto.QuizzResultFullDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, QuestionMapper.class})
public interface QuizzResultMapper {

    @Mapping(target = "student", source = "attendance.student")
    @Mapping(target = "numberOfQuestions", source = "quizz.numberOfQuestions")
    QuizzResultDto toDto(QuizzResult quizzResult);

    @Mapping(target = "numberOfQuestions", source = "quizz.numberOfQuestions")
    @Mapping(target = "questions", source = "quizz.questions")
    @Mapping(target = "name", source = "quizz.name")
    QuizzResultFullDto toFullDto(QuizzResult quizzResult);
}
