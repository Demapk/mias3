package ru.kpfu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.domain.QuestionResponse;

import java.util.List;

@Repository
public interface QuestionResponseRepository extends JpaRepository<QuestionResponse,Long>{

    @Query("select qr from QuestionResponse qr where (:surveyId is null or qr.survey.id = :surveyId)")
    List<QuestionResponse> findResponses(@Param("surveyId") Long surveyId);
}
