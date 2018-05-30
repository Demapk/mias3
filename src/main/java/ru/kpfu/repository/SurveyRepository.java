package ru.kpfu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.domain.Survey;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
