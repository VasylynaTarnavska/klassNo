package kindgeek.school.klassno.service.impl;

import kindgeek.school.klassno.entity.Question;
import kindgeek.school.klassno.entity.request.QuestionRequest;
import kindgeek.school.klassno.exception.NotFoundException;
import kindgeek.school.klassno.mapper.QuestionMapper;
import kindgeek.school.klassno.repository.QuestionRepository;
import kindgeek.school.klassno.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper mapper;
    private final QuestionRepository repository;

    @Override
    public Question create(QuestionRequest questionRequest) {
        return mapper.toEntity(questionRequest);
    }

    @Override
    public Question getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Question by id " + id + " not found"));
    }

    @Override
    public void delete(Question question) {
        repository.delete(question);

    }
}
