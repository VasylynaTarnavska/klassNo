package kindgeek.school.klassno.service;

import kindgeek.school.klassno.entity.Lesson;
import kindgeek.school.klassno.entity.dto.LessonDto;
import kindgeek.school.klassno.entity.dto.criteria.LessonCriteria;
import kindgeek.school.klassno.entity.request.LessonRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LessonService {
    LessonDto findDtoById(Long id);

    Lesson findById(Long id);

    Long create(LessonRequest lessonRequest);

    void delete(Long id);

    void edit(Long id, LessonRequest lessonRequest);

    Page<LessonDto> find(LessonCriteria lessonCriteria, Pageable page);

    Page<LessonDto> findLessonByStudentId(Long studentId, Pageable page);
}
