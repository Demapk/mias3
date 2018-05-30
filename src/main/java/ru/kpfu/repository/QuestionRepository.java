package ru.kpfu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long>{
}
