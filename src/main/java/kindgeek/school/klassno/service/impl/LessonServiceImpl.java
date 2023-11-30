package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Lesson;
import kindgeek.school.klassno.entity.dto.LessonDto;
import kindgeek.school.klassno.entity.dto.LessonShortDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    private final LessonMapper lessonMapper;

    private final AttendanceService attendanceService;

    private final LessonFilesStorageService lessonFilesStorageService;

    @Override
    public LessonDto getDtoById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lesson not found"));
        return lessonMapper.toDto(lesson);
    }

    @Override
    public Lesson getById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lesson not found"));
    }

    @Override
    @Transactional
    public Long create(LessonRequest lessonRequest) {
        Lesson lesson = lessonMapper.toEntity(lessonRequest);
        lesson.setLessonTime(lesson.getLessonTime().truncatedTo(ChronoUnit.MINUTES));
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
    public void save(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    @Override
    public void edit(Long id, LessonRequest lessonRequest) {
        Lesson lesson = getById(id);
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
    public List<LessonDto> getByTeacherIdWithoutQuiz(Long teacherId) {
        return lessonRepository.findByTeacherId(teacherId).stream()
                .filter(lesson -> Objects.isNull(lesson.getQuizz()))
                .map(lessonMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LessonDto> getByStudentId(Long studentId) {
        List<Lesson> lessons = lessonRepository.findByStudentId(studentId);
        return lessons.stream()
                .map((lessonMapper::toDto))
                .peek(lessonDto -> lessonDto.setMark(attendanceService
                        .getMarkByStudentIdAndLessonId(studentId, lessonDto.getId())))
                .sorted(Comparator.comparing(LessonDto::getLessonTime))
                .collect(Collectors.toList());
    }

    @Override
    public List<LessonShortDto> getShortInfo(Long classId, Long subjectId) {
        return lessonRepository.findByClassIdAndSubjectId(classId, subjectId)
                .stream()
                .map(lessonMapper::toShortDto)
                .peek(dto -> dto.setLessonTime(dto.getLessonTime().truncatedTo(ChronoUnit.DAYS)))
                .sorted(Comparator.comparing(LessonShortDto::getLessonTime))
                .collect(Collectors.toList());
    }
}
