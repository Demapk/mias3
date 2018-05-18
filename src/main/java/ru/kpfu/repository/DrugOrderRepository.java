package ru.kpfu.repository;

import ru.kpfu.domain.DrugOrder;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DrugOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrugOrderRepository extends JpaRepository<DrugOrder, Long> {

}
