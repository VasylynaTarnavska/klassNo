package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Lesson;
import kindgeek.school.klassno.entity.Teacher;
import kindgeek.school.klassno.entity.dto.LessonDto;
import kindgeek.school.klassno.entity.dto.TeacherDto;
import kindgeek.school.klassno.entity.dto.criteria.LessonCriteria;
import kindgeek.school.klassno.entity.dto.criteria.TeacherCriteria;
import kindgeek.school.klassno.entity.request.TeacherRequest;
import kindgeek.school.klassno.exception.NotFoundException;
import kindgeek.school.klassno.mapper.TeacherMapper;
import kindgeek.school.klassno.repository.TeacherRepository;
import kindgeek.school.klassno.repository.specification.LessonSpecification;
import kindgeek.school.klassno.repository.specification.TeacherSpecification;
import kindgeek.school.klassno.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;

    @Override
    public void create(TeacherRequest teacherRequest) {
        Teacher teacher = teacherMapper.toEntity(teacherRequest);
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
        return teacherMapper.toDto(teacher);
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

    @Override
    public Page<TeacherDto> find(TeacherCriteria teacherCriteria, Pageable page){
        TeacherSpecification teacherSpecification = new TeacherSpecification(teacherCriteria);
        Page<Teacher> teachers = teacherRepository.findAll(teacherSpecification, page);
        return teachers.map(teacherMapper::toDto);
    }
}
