package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Lesson;
import kindgeek.school.klassno.entity.Teacher;
import kindgeek.school.klassno.entity.dto.LessonDto;
import kindgeek.school.klassno.entity.dto.TeacherDto;
import kindgeek.school.klassno.entity.request.TeacherRequest;
import kindgeek.school.klassno.exception.NotFoundException;
import kindgeek.school.klassno.mapper.TeacherMapper;
import kindgeek.school.klassno.repository.TeacherRepository;
import kindgeek.school.klassno.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;

    @Override
    public void create(TeacherRequest teacherRequest) {
        Teacher teacher = createFromRequest(teacherRequest);
        teacherRepository.save(teacher);
    }

    @Override
    public Teacher findById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Teacher with id:" + id + " does not exist"));
    }

    @Override
    public TeacherDto findDtoById (Long id) {
        Teacher teacher =  teacherRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Teacher with id:" + id + " does not exist"));
        return toDto(teacher);
    }

    @Override
    public void delete (Long id){
        teacherRepository.deleteById(id);
    }

    @Override
    public void edit (Long id, TeacherRequest teacherRequest){
        Teacher teacher = findById(id);
        teacherMapper.update(teacher, teacherRequest);
        teacherRepository.save(teacher);
    }



    private Teacher createFromRequest (TeacherRequest teacherRequest) {
        Teacher teacher = new Teacher();
        teacher.setEmail(teacherRequest.getEmail());
        teacher.setLessonLink(teacherRequest.getLessonLink());
        teacher.setFirstName(teacherRequest.getFirstName());
        teacher.setLastName(teacherRequest.getLastName());
        teacher.setPassword(teacherRequest.getPassword());
        teacher.setAvatar(teacherRequest.getAvatar());
        return teacher;

    }

    private TeacherDto toDto(Teacher teacher) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacher.getId());
        teacherDto.setAvatar(teacher.getAvatar());
        teacherDto.setFirstName(teacher.getFirstName());
        teacherDto.setLastName(teacher.getLastName());
        teacherDto.setLessonLink(teacher.getLessonLink());
        return teacherDto;
    }
}
