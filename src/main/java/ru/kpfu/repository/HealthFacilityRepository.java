package ru.kpfu.repository;

import ru.kpfu.domain.HealthFacility;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HealthFacility entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HealthFacilityRepository extends JpaRepository<HealthFacility, Long> {

}
