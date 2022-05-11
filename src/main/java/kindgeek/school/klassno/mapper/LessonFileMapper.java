package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.LessonFile;
import kindgeek.school.klassno.entity.dto.LessonFileDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonFileMapper {

    LessonFileDto toDto(LessonFile lessonFile);

}
