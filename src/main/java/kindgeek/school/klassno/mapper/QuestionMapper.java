package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.Question;
import kindgeek.school.klassno.entity.Quizz;
import kindgeek.school.klassno.entity.dto.QuestionDto;
import kindgeek.school.klassno.entity.request.QuestionRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {AnswerMapper.class})
public interface QuestionMapper {

    Question toEntity(QuestionRequest questionRequest);

    QuestionDto toDto(Question question);


    @AfterMapping
    default void afterToEntity(@MappingTarget Question question, QuestionRequest questionRequest) {
        question.getAnswers().forEach(answer -> answer.setQuestion(question));
        Quizz quizz = new Quizz();
        quizz.setId(questionRequest.getQuizzId());
        question.setQuizz(quizz);
    }

}
