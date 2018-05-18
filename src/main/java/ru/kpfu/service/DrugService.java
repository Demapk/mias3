package ru.kpfu.service;

import ru.kpfu.domain.Drug;
import ru.kpfu.repository.DrugRepository;
import ru.kpfu.service.dto.DrugDTO;
import ru.kpfu.service.mapper.DrugMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Drug.
 */
@Service
@Transactional
public class DrugService {

    private final Logger log = LoggerFactory.getLogger(DrugService.class);

    private final DrugRepository drugRepository;

    private final DrugMapper drugMapper;

    public DrugService(DrugRepository drugRepository, DrugMapper drugMapper) {
        this.drugRepository = drugRepository;
        this.drugMapper = drugMapper;
    }

    /**
     * Save a drug.
     *
     * @param drugDTO the entity to save
     * @return the persisted entity
     */
    public DrugDTO save(DrugDTO drugDTO) {
        log.debug("Request to save Drug : {}", drugDTO);
        Drug drug = drugMapper.toEntity(drugDTO);
        drug = drugRepository.save(drug);
        return drugMapper.toDto(drug);
    }

    /**
     * Get all the drugs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DrugDTO> findAll() {
        log.debug("Request to get all Drugs");
        return drugRepository.findAll().stream()
            .map(drugMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one drug by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DrugDTO findOne(Long id) {
        log.debug("Request to get Drug : {}", id);
        Drug drug = drugRepository.findOne(id);
        return drugMapper.toDto(drug);
    }

    /**
     * Delete the drug by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Drug : {}", id);
        drugRepository.delete(id);
    }
}
