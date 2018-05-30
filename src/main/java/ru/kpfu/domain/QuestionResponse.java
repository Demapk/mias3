package ru.kpfu.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class QuestionResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_response_id_seq")
    @SequenceGenerator(name = "question_response_id_seq", sequenceName = "question_response_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JsonManagedReference
    private Survey survey;

    @ManyToOne
    @JsonManagedReference
    private Question question;

    private String answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
