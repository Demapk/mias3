package ru.kpfu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.domain.Question;
import ru.kpfu.repository.QuestionRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class QuestionService {

    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Transactional
    public Question findOne(Long id) {
        return questionRepository.findOne(id);
    }

    @Transactional
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public void delete(Long id) {
        questionRepository.delete(id);
    }
}
