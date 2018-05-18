package ru.kpfu.service;

import ru.kpfu.domain.ProcedureOrder;
import ru.kpfu.repository.ProcedureOrderRepository;
import ru.kpfu.service.dto.ProcedureOrderDTO;
import ru.kpfu.service.mapper.ProcedureOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProcedureOrder.
 */
@Service
@Transactional
public class ProcedureOrderService {

    private final Logger log = LoggerFactory.getLogger(ProcedureOrderService.class);

    private final ProcedureOrderRepository procedureOrderRepository;

    private final ProcedureOrderMapper procedureOrderMapper;

    public ProcedureOrderService(ProcedureOrderRepository procedureOrderRepository, ProcedureOrderMapper procedureOrderMapper) {
        this.procedureOrderRepository = procedureOrderRepository;
        this.procedureOrderMapper = procedureOrderMapper;
    }

    /**
     * Save a procedureOrder.
     *
     * @param procedureOrderDTO the entity to save
     * @return the persisted entity
     */
    public ProcedureOrderDTO save(ProcedureOrderDTO procedureOrderDTO) {
        log.debug("Request to save ProcedureOrder : {}", procedureOrderDTO);
        ProcedureOrder procedureOrder = procedureOrderMapper.toEntity(procedureOrderDTO);
        procedureOrder = procedureOrderRepository.save(procedureOrder);
        return procedureOrderMapper.toDto(procedureOrder);
    }

    /**
     * Get all the procedureOrders.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ProcedureOrderDTO> findAll() {
        log.debug("Request to get all ProcedureOrders");
        return procedureOrderRepository.findAll().stream()
            .map(procedureOrderMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one procedureOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ProcedureOrderDTO findOne(Long id) {
        log.debug("Request to get ProcedureOrder : {}", id);
        ProcedureOrder procedureOrder = procedureOrderRepository.findOne(id);
        return procedureOrderMapper.toDto(procedureOrder);
    }

    /**
     * Delete the procedureOrder by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcedureOrder : {}", id);
        procedureOrderRepository.delete(id);
    }
}
