package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Attendance;
import kindgeek.school.klassno.entity.dto.AttendanceDto;
import kindgeek.school.klassno.entity.dto.LessonDto;
import kindgeek.school.klassno.entity.request.AttendanceRequest;
import kindgeek.school.klassno.exception.NotFoundException;
import kindgeek.school.klassno.mapper.AttendanceMapper;
import kindgeek.school.klassno.repository.AttendanceRepository;
import kindgeek.school.klassno.service.AttendanceService;
import kindgeek.school.klassno.service.LessonService;
import kindgeek.school.klassno.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final StudentService studentService;

    private final LessonService lessonService;

    private final AttendanceRepository attendanceRepository;

    private  final AttendanceMapper attendanceMapper;

    @Override
    public void create(AttendanceRequest attendanceRequest) {
        Attendance attendance = attendanceMapper.toEntity(attendanceRequest);
        attendanceRepository.save(attendance);
    }

    @Override
    public AttendanceDto findDtoById(Long id) {
        Attendance attendance = findById(id);
        return attendanceMapper.toDto(attendance);
    }

    @Override
    public Attendance findById(Long id) {
        return  attendanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Attendance with id:" + id + " does not exist"));
    }

    @Override
    public void editPresent(Long id, Boolean isPresent){
        Attendance attendance = findById(id);
        attendance.setIsPresent(isPresent);
        attendanceRepository.save(attendance);
    }

    @Override
    public List<AttendanceDto> findByLessonId(Long id){
        List<Attendance> attendances = attendanceRepository.findByLessonId(id);
        return attendances.stream()
                .map(attendanceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id){attendanceRepository.deleteById(id);}


    private Attendance createFromRequest(AttendanceRequest attendanceRequest) {
        Attendance attendance = new Attendance();
        attendance.setIsPresent(attendanceRequest.getIsPresent());
        attendance.setHomeWork(attendanceRequest.getHomeWork());
        attendance.setStudent(studentService.findById(attendanceRequest.getStudentId()));
        attendance.setLesson(lessonService.findById(attendanceRequest.getLessonId()));
        return attendance;
    }

    private AttendanceDto toDto(Attendance attendance){
        AttendanceDto attendanceDto = new AttendanceDto();
        attendanceDto.setIsPresent(attendance.getIsPresent());
        attendanceDto.setHomeWork(attendance.getHomeWork());
        return attendanceDto;
    }
}

