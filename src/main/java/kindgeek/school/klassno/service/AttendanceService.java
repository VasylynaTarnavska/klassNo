package kindgeek.school.klassno.service;

import kindgeek.school.klassno.entity.Attendance;
import kindgeek.school.klassno.entity.Lesson;
import kindgeek.school.klassno.entity.dto.AttendanceDto;
import kindgeek.school.klassno.entity.request.AttendanceRequest;

import java.util.List;

public interface AttendanceService {
    void create(AttendanceRequest attendanceRequest);

    void createFromLesson(Lesson lesson);

    AttendanceDto findDtoById(Long id);

    Attendance findById(Long id);

    void editPresent(Long id, Boolean isPresent);

    List<AttendanceDto> findByLessonId(Long id);

    void delete(Long id);
    Byte getMarkByStudentIdAndLessonId(Long studentId, Long lessonId);

    Attendance getByStudentIdAndQuizzId(Long studentId, Long quizzId);
}
