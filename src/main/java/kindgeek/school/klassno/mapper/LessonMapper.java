package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.Lesson;
import kindgeek.school.klassno.entity.dto.LessonDto;
import kindgeek.school.klassno.entity.request.LessonRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {SubjectMapper.class, ClassRoomMapper.class, TeacherMapper.class})
public interface LessonMapper {

    @Mapping(target = "subject", source = "subjectId")
    @Mapping(target = "classRoom", source = "classRoomId")
    @Mapping(target = "teacher", source = "teacherId")
    Lesson toEntity(LessonRequest lessonRequest);

    @Mapping(target = "subject", source = "subjectId")
    @Mapping(target = "classRoom", source = "classRoomId")
    @Mapping(target = "teacher", source = "teacherId")
    void update(@MappingTarget Lesson lesson, LessonRequest lessonRequest);

    @Mapping(target = "classGrade", source = "classRoom.className")
    LessonDto toDto(Lesson lesson);

    default Lesson fromId(Long id){
        if (id == null) {
            return null;
        }

        Lesson lesson = new Lesson();
        lesson.setId(id);
        return lesson;
    }
}
