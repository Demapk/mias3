package ru.kpfu.service;

import ru.kpfu.domain.DrugOrder;
import ru.kpfu.repository.DrugOrderRepository;
import ru.kpfu.service.dto.DrugOrderDTO;
import ru.kpfu.service.mapper.DrugOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DrugOrder.
 */
@Service
@Transactional
public class DrugOrderService {

    private final Logger log = LoggerFactory.getLogger(DrugOrderService.class);

    private final DrugOrderRepository drugOrderRepository;

    private final DrugOrderMapper drugOrderMapper;

    public DrugOrderService(DrugOrderRepository drugOrderRepository, DrugOrderMapper drugOrderMapper) {
        this.drugOrderRepository = drugOrderRepository;
        this.drugOrderMapper = drugOrderMapper;
    }

    /**
     * Save a drugOrder.
     *
     * @param drugOrderDTO the entity to save
     * @return the persisted entity
     */
    public DrugOrderDTO save(DrugOrderDTO drugOrderDTO) {
        log.debug("Request to save DrugOrder : {}", drugOrderDTO);
        DrugOrder drugOrder = drugOrderMapper.toEntity(drugOrderDTO);
        drugOrder = drugOrderRepository.save(drugOrder);
        return drugOrderMapper.toDto(drugOrder);
    }

    /**
     * Get all the drugOrders.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DrugOrderDTO> findAll() {
        log.debug("Request to get all DrugOrders");
        return drugOrderRepository.findAll().stream()
            .map(drugOrderMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one drugOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DrugOrderDTO findOne(Long id) {
        log.debug("Request to get DrugOrder : {}", id);
        DrugOrder drugOrder = drugOrderRepository.findOne(id);
        return drugOrderMapper.toDto(drugOrder);
    }

    /**
     * Delete the drugOrder by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DrugOrder : {}", id);
        drugOrderRepository.delete(id);
    }
}
