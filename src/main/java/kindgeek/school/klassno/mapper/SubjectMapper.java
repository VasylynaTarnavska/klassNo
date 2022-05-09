package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.Subject;
import kindgeek.school.klassno.entity.Teacher;
import kindgeek.school.klassno.entity.dto.SubjectDto;
import kindgeek.school.klassno.entity.request.SubjectRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ClassRoomMapper.class)
public interface SubjectMapper {

    Subject toEntity(SubjectRequest subjectRequest);

    SubjectDto toDto(Subject subject );

    void update(@MappingTarget Subject subject, SubjectRequest subjectRequest);

    default Subject fromId(Long id){
        if (id == null) {
            return null;
        }

        Subject subject = new Subject();
        subject.setId(id);
        return subject;
    }
}
