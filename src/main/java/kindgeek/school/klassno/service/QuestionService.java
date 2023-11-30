package kindgeek.school.klassno.service;

import kindgeek.school.klassno.entity.Question;
import kindgeek.school.klassno.entity.request.QuestionRequest;

public interface QuestionService {

    Question create(QuestionRequest questionRequest);

    Question getById(Long id);

    void delete(Question question);
}
