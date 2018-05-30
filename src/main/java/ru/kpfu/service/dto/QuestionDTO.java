package ru.kpfu.service.dto;

import java.util.Set;

public class QuestionDTO {
    private Long id;
    private String title;
    private String questionType;
    private boolean required;
    private Set<String> questionOptions;
}
