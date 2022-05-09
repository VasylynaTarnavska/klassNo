package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.Mark;
import kindgeek.school.klassno.entity.StudentAward;
import kindgeek.school.klassno.entity.dto.StudentAwardDto;
import kindgeek.school.klassno.entity.request.MarkRequest;
import kindgeek.school.klassno.entity.request.StudentAwardRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {AwardMapper.class, StudentMapper.class, TeacherMapper.class})
public interface StudentAwardMapper {

    @Mapping(target = "award", source = "awardId")
    @Mapping(target = "student", source = "studentId")
    @Mapping(target = "teacher", source = "teacherId")
    StudentAward toEntity(StudentAwardRequest studentAwardRequest);

    @Mapping(target = "award", source = "awardId")
    @Mapping(target = "student", source = "studentId")
    @Mapping(target = "teacher", source = "teacherId")
    void update(@MappingTarget StudentAward studentAward, StudentAwardRequest studentAwardRequest);


    StudentAwardDto toDto(StudentAward studentAward);
}
