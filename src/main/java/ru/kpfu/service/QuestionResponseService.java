package ru.kpfu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.domain.QuestionResponse;
import ru.kpfu.repository.QuestionResponseRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class QuestionResponseService {

    @Autowired
    private QuestionResponseRepository questionResponseRepository;

    public QuestionResponse save(QuestionResponse questionResponse) {
        return questionResponseRepository.save(questionResponse);
    }

    public List<QuestionResponse> save(Iterable<QuestionResponse> questionResponses) {
        return questionResponseRepository.save(questionResponses);
    }

    public QuestionResponse findOne(Long id) {
        return questionResponseRepository.findOne(id);
    }

    public List<QuestionResponse> findAll() {
        return questionResponseRepository.findAll();
    }

    public void delete(Long id) {
        questionResponseRepository.delete(id);
    }

    public List<QuestionResponse> findByParam(Long surveyId) {
        return questionResponseRepository.findResponses(surveyId);
    }
}
