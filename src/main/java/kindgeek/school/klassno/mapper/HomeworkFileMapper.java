package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.HomeworkFile;
import kindgeek.school.klassno.entity.dto.HomeworkFileDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HomeworkFileMapper {
    HomeworkFileDto toDto(HomeworkFile homeworkFile);
}
