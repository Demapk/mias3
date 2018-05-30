package ru.kpfu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.kpfu.domain.Doctor;
import ru.kpfu.domain.Question;
import ru.kpfu.domain.Survey;
import ru.kpfu.domain.SurveyPage;
import ru.kpfu.repository.DoctorRepository;
import ru.kpfu.repository.QuestionRepository;
import ru.kpfu.repository.SurveyRepository;
import ru.kpfu.service.dto.DoctorDTO;
import ru.kpfu.service.dto.SurveyDTO;
import ru.kpfu.service.mapper.DoctorMapper;
import ru.kpfu.service.mapper.SurveyMapper;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SurveyService{

    private final Logger log = LoggerFactory.getLogger(SurveyService.class);

    private SurveyRepository surveyRepository;

    private SurveyMapper surveyMapper;

    public SurveyService(SurveyRepository surveyRepository, SurveyMapper surveyMapper) {
        this.surveyRepository = surveyRepository;
        this.surveyMapper = surveyMapper;
    }

    public SurveyDTO save(SurveyDTO surveyDTO) {
        log.debug("Request to save Doctor : {}", surveyDTO);
        Survey survey = surveyMapper.toEntity(surveyDTO);
        survey = surveyRepository.save(survey);
        return surveyMapper.toDto(survey);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public SurveyDTO findOne(Long id) {
        log.debug("Request to get Survey : {}", id);
        Survey survey = surveyRepository.findOne(id);
        return surveyMapper.toDto(survey);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Page<SurveyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Surveys");
        return surveyRepository.findAll(pageable)
            .map(surveyMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Survey : {}", id);
        surveyRepository.delete(id);
    }

    public SurveyDTO createSurvey(SurveyDTO surveyDTO) {
        Survey survey= new Survey();
        survey.setTitle(surveyDTO.getTitle());
        survey.setDescription(surveyDTO.getDescription());
        List<SurveyPage> surveyPages = new ArrayList<>();


        survey.setSurveyPages(surveyPages);
        survey.setActive(true);
        survey = surveyRepository.save(survey);
        return surveyDTO;
    }
}
