package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.Mark;
import kindgeek.school.klassno.entity.Student;
import kindgeek.school.klassno.entity.dto.MarkDto;
import kindgeek.school.klassno.entity.request.MarkRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {AttendanceMapper.class, StudentMapper.class})
public interface MarkMapper {

    @Mapping(target = "attendance", source = "attendanceId")
    @Mapping(target = "student", source = "studentId")
    Mark toEntity(MarkRequest markRequest);

    @Mapping(target = "attendance", source = "attendanceId")
    @Mapping(target = "student", source = "studentId")
    void update(@MappingTarget Mark mark, MarkRequest markRequest);

    MarkDto toDto(Mark mark);

    default Mark fromId(Long id){
        if (id == null) {
            return null;
        }

        Mark mark= new Mark();
        mark.setId(id);
        return mark;
    }
}
