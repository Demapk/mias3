package ru.kpfu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.domain.SurveyPage;

public interface SurveyPageRepository extends JpaRepository<SurveyPage,Long> {
}
