package kindgeek.school.klassno.repository.specification;

import kindgeek.school.klassno.entity.Teacher;
import kindgeek.school.klassno.entity.dto.criteria.TeacherCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TeacherSpecification implements Specification<Teacher> {

    private TeacherCriteria teacherCriteria;

    public TeacherSpecification(TeacherCriteria criteria) {
        this.teacherCriteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Teacher> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (teacherCriteria.getClassRoomId() != null) {
            predicates.add(criteriaBuilder.equal(root.join("lessons").join("classRoom").get("id"), teacherCriteria.getClassRoomId()));
        }

        if (teacherCriteria.getSubjectId() != null) {
            predicates.add(criteriaBuilder.equal(root.join("lessons").join("subject").get("id"), teacherCriteria.getSubjectId()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
