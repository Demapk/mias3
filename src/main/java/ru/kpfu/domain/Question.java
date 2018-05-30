package ru.kpfu.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotEmpty;
import ru.kpfu.domain.enumeration.QuestionType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(of = "id")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_id_seq")
    @SequenceGenerator(name = "question_id_seq", sequenceName = "question_id_seq", allocationSize = 1)
    private Long id;

    @NotEmpty
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    private QuestionType questionType;

    private boolean required;

    @ElementCollection
    @CollectionTable(name = "question_question_option", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "question_option")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<String> questionOptions = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<QuestionResponse> questionResponses = new ArrayList<>();

    @ManyToMany(mappedBy = "questions")
    @JsonBackReference
    private List<SurveyPage> surveyPages = new ArrayList<>();

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

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public List<String> getQuestionOptions() {
        return questionOptions;
    }

    public void setQuestionOptions(List<String> questionOptions) {
        this.questionOptions = questionOptions;
    }

    public List<QuestionResponse> getQuestionResponses() {
        return questionResponses;
    }

    public void setQuestionResponses(List<QuestionResponse> questionResponses) {
        this.questionResponses = questionResponses;
    }

    public List<SurveyPage> getSurveyPages() {
        return surveyPages;
    }

    public void setSurveyPages(List<SurveyPage> surveyPages) {
        this.surveyPages = surveyPages;
    }
}
