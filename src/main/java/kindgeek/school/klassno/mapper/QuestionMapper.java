package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.Question;
import kindgeek.school.klassno.entity.Quizz;
import kindgeek.school.klassno.entity.dto.QuestionDto;
import kindgeek.school.klassno.entity.dto.QuestionFullDto;
import kindgeek.school.klassno.entity.request.QuestionRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AnswerMapper.class, QuestionResultMapper.class})
public interface QuestionMapper {

    Question toEntity(QuestionRequest questionRequest);

    @Named(value = "toQuestionDto")
    QuestionDto toDto(Question question);

    @Mapping(target = "questionResult", source = "studentResult")
    QuestionFullDto toFullDto(Question question);


    @AfterMapping
    default void afterToEntity(@MappingTarget Question question, QuestionRequest questionRequest) {
        question.getAnswers().forEach(answer -> answer.setQuestion(question));
        Quizz quizz = new Quizz();
        quizz.setId(questionRequest.getQuizzId());
        question.setQuizz(quizz);
    }

}
