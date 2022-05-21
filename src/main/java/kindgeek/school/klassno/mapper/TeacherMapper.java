package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.Award;
import kindgeek.school.klassno.entity.Teacher;
import kindgeek.school.klassno.entity.dto.TeacherDto;
import kindgeek.school.klassno.entity.request.TeacherRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(target = "password", ignore = true)
    Teacher toEntity(TeacherRequest teacherRequest);

    TeacherDto toDto(Teacher teacher );

    void update(@MappingTarget Teacher teacher, TeacherRequest teacherRequest);

    default Teacher fromId(Long id){
        if (id == null) {
            return null;
        }

        Teacher teacher = new Teacher();
        teacher.setId(id);
        return teacher;
    }

}
