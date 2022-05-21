package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Lesson;
import kindgeek.school.klassno.entity.dto.LessonDto;
import kindgeek.school.klassno.entity.dto.criteria.LessonCriteria;
import kindgeek.school.klassno.entity.request.LessonRequest;
import kindgeek.school.klassno.exception.NotFoundException;
import kindgeek.school.klassno.mapper.LessonMapper;
import kindgeek.school.klassno.repository.LessonRepository;
import kindgeek.school.klassno.repository.specification.LessonSpecification;
import kindgeek.school.klassno.service.AttendanceService;
import kindgeek.school.klassno.service.LessonFilesStorageService;
import kindgeek.school.klassno.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    private final LessonMapper lessonMapper;

    private final AttendanceService attendanceService;

    private final LessonFilesStorageService lessonFilesStorageService;

    @Override
    public LessonDto findDtoById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lesson not found"));
        return lessonMapper.toDto(lesson);
    }

    @Override
    public Lesson findById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lesson not found"));
    }

    @Override
    @Transactional
    public Long create(LessonRequest lessonRequest) {
        Lesson lesson = lessonMapper.toEntity(lessonRequest);
        attendanceService.createFromLesson(lesson);
        lessonRepository.save(lesson);
        lessonFilesStorageService.saveFiles(Set.of(lessonRequest.getFile()), lesson.getId());
        return lesson.getId();
    }

    @Override
    public void delete(Long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public void edit(Long id, LessonRequest lessonRequest) {
        Lesson lesson = findById(id);
        lessonMapper.update(lesson, lessonRequest);
        lessonRepository.save(lesson);

    }

    @Override
    public Page<LessonDto> find(LessonCriteria lessonCriteria, Pageable page) {
        LessonSpecification lessonSpecification = new LessonSpecification(lessonCriteria);
        Page<Lesson> lessons = lessonRepository.findAll(lessonSpecification, page);
        return lessons.map(lessonMapper::toDto);
    }

    @Override
    public Page<LessonDto> findLessonByStudentId(Long studentId, Pageable page){
        Page<Lesson> lessons = lessonRepository.findLessonByStudentId(studentId, page);
        return lessons.map(lessonMapper::toDto);
    }
}
