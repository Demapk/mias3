package ru.kpfu.service.mapper;

import ru.kpfu.domain.Doctor;
import ru.kpfu.domain.Survey;
import ru.kpfu.service.dto.DoctorDTO;
import ru.kpfu.service.dto.SurveyDTO;

public interface SurveyMapper extends EntityMapper<SurveyDTO,Survey> {
    Survey toEntity(SurveyDTO surveyDTO);

    default Survey fromId(Long id) {
        if (id == null) {
            return null;
        }
        Survey survey = new Survey();
        survey.setId(id);
        return survey;
    }
}
