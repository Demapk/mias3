package ru.kpfu.repository;

import ru.kpfu.domain.Diseas;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Diseas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiseasRepository extends JpaRepository<Diseas, Long> {

}
