package ru.kpfu.repository;

import ru.kpfu.domain.ProcedureOrder;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProcedureOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcedureOrderRepository extends JpaRepository<ProcedureOrder, Long> {

}
