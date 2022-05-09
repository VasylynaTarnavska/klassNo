package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Lesson;
import kindgeek.school.klassno.entity.dto.LessonDto;
import kindgeek.school.klassno.entity.dto.criteria.LessonCriteria;
import kindgeek.school.klassno.entity.request.LessonRequest;
import kindgeek.school.klassno.exception.NotFoundException;
import kindgeek.school.klassno.mapper.LessonMapper;
import kindgeek.school.klassno.repository.LessonRepository;
import kindgeek.school.klassno.repository.specification.LessonSpecification;
import kindgeek.school.klassno.service.ClassRoomService;
import kindgeek.school.klassno.service.LessonService;
import kindgeek.school.klassno.service.SubjectService;
import kindgeek.school.klassno.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    private final TeacherService teacherService;

    private final SubjectService subjectService;

    private final ClassRoomService classRoomService;

    private final LessonMapper lessonMapper;

    @Override
    public LessonDto findDtoById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lesson not found"));
        return toDto(lesson);
    }

    @Override
    public Lesson findById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lesson not found"));
    }

    @Override
    public void create(LessonRequest lessonRequest) {
        Lesson lesson = createFromRequest(lessonRequest);
        lessonRepository.save(lesson);
    }

    @Override
    public void delete(Long id){
        lessonRepository.deleteById(id);
    }

    @Override
    public void edit(Long id, LessonRequest lessonRequest){
        Lesson lesson = findById(id);
        lessonMapper.update(lesson, lessonRequest);
        lessonRepository.save(lesson) ;

    }

    @Override
    public Page<LessonDto> find (LessonCriteria lessonCriteria, Pageable page){
        LessonSpecification lessonSpecification = new LessonSpecification(lessonCriteria);
        Page<Lesson> lessons = lessonRepository.findAll(lessonSpecification, page);
        return lessons.map(lessonMapper::toDto);
    }

    private Lesson createFromRequest(LessonRequest lessonRequest) {
        Lesson lesson = new Lesson();
        lesson.setDescription(lessonRequest.getDescription());
        lesson.setTopic(lessonRequest.getTopic());
        lesson.setLessonTime(lessonRequest.getLessonTime());
        lesson.setTeacher(teacherService.findById(lessonRequest.getTeacherId()));
        lesson.setSubject(subjectService.findById(lessonRequest.getSubjectId()));
        lesson.setClassRoom(classRoomService.findById(lessonRequest.getClassRoomId()));
        return lesson;
    }


    private LessonDto toDto(Lesson lesson) {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(lesson.getId());
        lessonDto.setDescription(lesson.getDescription());
        lessonDto.setLessonTime(lesson.getLessonTime());
        lessonDto.setTopic(lesson.getTopic());
        return lessonDto;
    }

}
