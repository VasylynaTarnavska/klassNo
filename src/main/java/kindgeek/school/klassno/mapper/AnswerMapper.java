package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.Answer;
import kindgeek.school.klassno.entity.dto.AnswerDto;
import kindgeek.school.klassno.entity.request.AnswerRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    Answer toEntity(AnswerRequest answerRequest);

    AnswerDto toDto(Answer answer);

}
