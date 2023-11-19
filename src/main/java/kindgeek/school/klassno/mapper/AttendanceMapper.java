package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.Attendance;
import kindgeek.school.klassno.entity.Student;
import kindgeek.school.klassno.entity.dto.AttendanceDto;
import kindgeek.school.klassno.entity.dto.AttendanceShortDto;
import kindgeek.school.klassno.entity.request.AttendanceRequest;
import org.apache.catalina.LifecycleState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {LessonMapper.class, StudentMapper.class})
public interface AttendanceMapper {

    @Mapping(target = "lesson", source = "lessonId")
    @Mapping(target = "student", source = "studentId")
    Attendance toEntity(AttendanceRequest attendanceRequest);

    @Mapping(target = "lesson", source = "lessonId")
    @Mapping(target = "student", source = "studentId")
    void update(@MappingTarget Attendance attendance, AttendanceRequest attendanceRequest);

    AttendanceDto toDto(Attendance attendance);

    @Mapping(target = "lessonId",  source = "lesson.id")
    @Mapping(target = "lessonTime",  source = "lesson.lessonTime")
    @Mapping(target = "topic",  source = "lesson.topic")
    AttendanceShortDto toShortDto(Attendance attendance);

    List<AttendanceShortDto> toShortDtoList(List<Attendance> attendances);

    default Attendance fromId(Long id){
        if (id == null) {
            return null;
        }

        Attendance attendance= new Attendance();
        attendance.setId(id);
        return attendance;
    }
}
