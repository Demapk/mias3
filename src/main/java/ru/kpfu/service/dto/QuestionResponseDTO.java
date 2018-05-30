package ru.kpfu.service.dto;

import ru.kpfu.domain.Survey;

import java.util.Set;

public class QuestionResponseDTO {
    private Long id;
    private String answer;
    private Long questionId;
    private Set<String> questionOptions;
}
