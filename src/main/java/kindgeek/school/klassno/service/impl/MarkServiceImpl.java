package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Mark;
import kindgeek.school.klassno.entity.dto.MarkDto;
import kindgeek.school.klassno.entity.request.MarkRequest;
import kindgeek.school.klassno.enums.Level;
import kindgeek.school.klassno.exception.NotFoundException;
import kindgeek.school.klassno.mapper.MarkMapper;
import kindgeek.school.klassno.repository.MarkRepository;
import kindgeek.school.klassno.service.AttendanceService;
import kindgeek.school.klassno.service.MarkService;
import kindgeek.school.klassno.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;

    private final StudentService studentService;

    private final AttendanceService attendanceService;

    private final MarkMapper markMapper;

    @Override
    public void create(MarkRequest markRequest){
        Mark mark = markMapper.toEntity(markRequest);
        markRepository.save(mark);
    }

    @Override
    public MarkDto findDtoById(Long id){
        Mark mark =  findById(id);
        return markMapper.toDto(mark);
    }

    @Override
    public Mark findById(Long id){
        return markRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Mark with id:" + id + " does not exist"));
    }

    @Override
    public void delete(Long id){
        markRepository.deleteById(id);
    }

    @Override
    public List<MarkDto> findByStudentId(Long studentId){
        List <Mark> marks = markRepository.findByStudentId(studentId);
        return marks.stream()
                .map(markMapper::toDto)
                .collect(Collectors.toList()); //todo special Dto
    }

    @Override
    public List<MarkDto> findBySubjectIdAndClassRoomId(Long subjectId, Long classRoomId){
        List <Mark> marks = markRepository.findBySubjectIdAndClassRoomId(subjectId, classRoomId);
        return marks.stream()
                .map(markMapper::toDto)
                .collect(Collectors.toList()); //todo special Dto
    }

    @Override
    public void edit(Long id, MarkRequest markRequest){
        Mark mark = findById(id);
        markMapper.update(mark, markRequest);
        markRepository.save(mark);
    }


    private Mark createFromRequest(MarkRequest markRequest){
        Mark mark = new Mark();
        mark.setLevel(Level.valueOf(markRequest.getLevel()));
        mark.setComment(markRequest.getComment());
        mark.setStudent(studentService.findById(markRequest.getStudentId()));
        mark.setAttendance(attendanceService.findById(markRequest.getAttendanceId()));
        return mark;
    }

    private MarkDto toDto (Mark mark){
        MarkDto markDto = new MarkDto();
        markDto.setLevel(mark.getLevel().toString());
        markDto.setComment(mark.getComment());
        return markDto;
    }


}
