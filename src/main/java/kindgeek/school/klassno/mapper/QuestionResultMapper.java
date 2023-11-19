package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.QuestionResult;
import kindgeek.school.klassno.entity.dto.QuestionResultDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AnswerMapper.class})
public interface QuestionResultMapper {

    QuestionResultDto toDto(QuestionResult questionResult);

}
