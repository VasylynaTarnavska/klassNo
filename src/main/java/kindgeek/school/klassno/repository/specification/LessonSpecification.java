package kindgeek.school.klassno.repository.specification;

import kindgeek.school.klassno.entity.Lesson;
import kindgeek.school.klassno.entity.dto.criteria.LessonCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LessonSpecification implements Specification<Lesson> {

    private LessonCriteria lessonCriteria;
    public LessonSpecification(LessonCriteria criteria) {
        this.lessonCriteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Lesson> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        //if(!StringUtils.isBlank(firstName)
        if(lessonCriteria.getClassRoomId()!= null){
            predicates.add(criteriaBuilder.equal(root.join("classRoom").get("id"), lessonCriteria.getClassRoomId()));
        }

        if(lessonCriteria.getSubjectId() != null){
            predicates.add(criteriaBuilder.equal(root.join("subject").get("id"), lessonCriteria.getSubjectId() ));
        }

        if(lessonCriteria.getTeacherId() != null){
            predicates.add(criteriaBuilder.equal(root.join("teacher").get("id"), lessonCriteria.getTeacherId() ));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
