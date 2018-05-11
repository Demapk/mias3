package ru.kpfu.service;

import ru.kpfu.domain.HealthFacility;
import ru.kpfu.repository.HealthFacilityRepository;
import ru.kpfu.service.dto.HealthFacilityDTO;
import ru.kpfu.service.mapper.HealthFacilityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing HealthFacility.
 */
@Service
@Transactional
public class HealthFacilityService {

    private final Logger log = LoggerFactory.getLogger(HealthFacilityService.class);

    private final HealthFacilityRepository healthFacilityRepository;

    private final HealthFacilityMapper healthFacilityMapper;

    public HealthFacilityService(HealthFacilityRepository healthFacilityRepository, HealthFacilityMapper healthFacilityMapper) {
        this.healthFacilityRepository = healthFacilityRepository;
        this.healthFacilityMapper = healthFacilityMapper;
    }

    /**
     * Save a healthFacility.
     *
     * @param healthFacilityDTO the entity to save
     * @return the persisted entity
     */
    public HealthFacilityDTO save(HealthFacilityDTO healthFacilityDTO) {
        log.debug("Request to save HealthFacility : {}", healthFacilityDTO);
        HealthFacility healthFacility = healthFacilityMapper.toEntity(healthFacilityDTO);
        healthFacility = healthFacilityRepository.save(healthFacility);
        return healthFacilityMapper.toDto(healthFacility);
    }

    /**
     * Get all the healthFacilities.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<HealthFacilityDTO> findAll() {
        log.debug("Request to get all HealthFacilities");
        return healthFacilityRepository.findAll().stream()
            .map(healthFacilityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one healthFacility by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public HealthFacilityDTO findOne(Long id) {
        log.debug("Request to get HealthFacility : {}", id);
        HealthFacility healthFacility = healthFacilityRepository.findOne(id);
        return healthFacilityMapper.toDto(healthFacility);
    }

    /**
     * Delete the healthFacility by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete HealthFacility : {}", id);
        healthFacilityRepository.delete(id);
    }
}
