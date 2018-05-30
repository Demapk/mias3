package ru.kpfu.service.dto;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import ru.kpfu.domain.Question;
import ru.kpfu.domain.QuestionResponse;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@ToString(exclude = {"questions"})
public class SurveyDTO{
    private Long id;
    @NotEmpty
    private String title;
    private String description;
    private List<QuestionDTO> questionDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuestionDTO> getQuestionDTO() {
        return questionDTO;
    }

    public void setQuestionDTO(List<QuestionDTO> questionDTO) {
        this.questionDTO = questionDTO;
    }
}
