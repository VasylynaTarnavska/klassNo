package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.Award;
import kindgeek.school.klassno.entity.Mark;
import kindgeek.school.klassno.entity.Student;
import kindgeek.school.klassno.entity.dto.StudentDto;
import kindgeek.school.klassno.entity.request.MarkRequest;
import kindgeek.school.klassno.entity.request.StudentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ClassRoomMapper.class)
public interface StudentMapper {

    @Mapping(target = "classRoom", source = "classRoomId")
    Student toEntity(StudentRequest studentRequest);

    StudentDto toDto(Student student);

    @Mapping(target = "classRoom",  ignore = true)
    @Mapping(target = "password", ignore = true)
    void update(@MappingTarget Student student, StudentRequest studentRequest);

    default Student fromId(Long id){
        if (id == null) {
            return null;
        }

        Student student= new Student();
        student.setId(id);
        return student;
    }


}

