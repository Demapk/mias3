package ru.kpfu.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class SurveyPage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "survey_page_id_seq")
    @SequenceGenerator(name = "survey_page_id_seq", sequenceName = "survey_page_id_seq", allocationSize = 1)
    private Long id;

    private String title;

    private Long pos;

    @ManyToOne
    @JsonManagedReference
    private Survey survey;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "survey_page_question",
        joinColumns = @JoinColumn(name = "survey_page_id"),
        inverseJoinColumns = @JoinColumn(name = "question_id"))

    @JsonBackReference
    private List<Question> questions = new ArrayList<>();

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

    public Long getPos() {
        return pos;
    }

    public void setPos(Long pos) {
        this.pos = pos;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
