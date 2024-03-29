package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Attendance;
import kindgeek.school.klassno.entity.Lesson;
import kindgeek.school.klassno.entity.Student;
import kindgeek.school.klassno.entity.dto.AttendanceDto;
import kindgeek.school.klassno.entity.request.AttendanceRequest;
import kindgeek.school.klassno.exception.NotFoundException;
import kindgeek.school.klassno.mapper.AttendanceMapper;
import kindgeek.school.klassno.repository.AttendanceRepository;
import kindgeek.school.klassno.service.AttendanceService;
import kindgeek.school.klassno.service.LessonService;
import kindgeek.school.klassno.service.StudentService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final StudentService studentService;
    private final LessonService lessonService;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;

    public AttendanceServiceImpl(StudentService studentService,
                                 @Lazy LessonService lessonService,
                                 AttendanceRepository attendanceRepository,
                                 AttendanceMapper attendanceMapper) {
        this.studentService = studentService;
        this.lessonService = lessonService;
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
    }


    @Override
    public void create(AttendanceRequest attendanceRequest) {
        Attendance attendance = attendanceMapper.toEntity(attendanceRequest);
        attendanceRepository.save(attendance);
    }

    @Override
    public void createFromLesson(Lesson lesson) {
        List<Attendance> attendances = studentService.findByClassRoomId(lesson.getClassRoom().getId())
                .stream().map(student -> createAttendance(lesson, student))
                .collect(Collectors.toList());
        attendanceRepository.saveAll(attendances);
    }

    private Attendance createAttendance(Lesson lesson, Student student) {
        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setLesson(lesson);
        attendance.setHomeWork(lesson.getHomework());
        return attendance;
    }

    @Override
    public AttendanceDto findDtoById(Long id) {
        Attendance attendance = findById(id);
        return attendanceMapper.toDto(attendance);
    }

    @Override
    public Attendance findById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Attendance with id:" + id + " does not exist"));
    }

    @Override
    public void editPresent(Long id, Boolean isPresent) {
        Attendance attendance = findById(id);
        attendance.setIsPresent(isPresent);
        attendanceRepository.save(attendance);
    }

    @Override
    public List<AttendanceDto> findByLessonId(Long id) {
        List<Attendance> attendances = attendanceRepository.findByLessonId(id);
        return attendances.stream()
                .map(attendanceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        attendanceRepository.deleteById(id);
    }

    @Override
    public Byte getMarkByStudentIdAndLessonId(Long studentId, Long lessonId) {
        return attendanceRepository.findMarkByStudentIdAndLessonId(studentId, lessonId);
    }

    @Override
    public Attendance getByStudentIdAndQuizzId(Long studentId, Long quizzId) {
        return attendanceRepository.findByStudentIdAndQuizzId(studentId, quizzId)
                .orElseThrow(() -> new NotFoundException("Attendance not found"));
    }
}

