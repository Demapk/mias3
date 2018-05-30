package ru.kpfu.service;

import ru.kpfu.domain.Facility;
import ru.kpfu.repository.FacilityRepository;
import ru.kpfu.service.dto.FacilityDTO;
import ru.kpfu.service.mapper.FacilityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Facility.
 */
@Service
@Transactional
public class FacilityService {

    private final Logger log = LoggerFactory.getLogger(FacilityService.class);

    private final FacilityRepository facilityRepository;

    private final FacilityMapper facilityMapper;

    public FacilityService(FacilityRepository facilityRepository, FacilityMapper facilityMapper) {
        this.facilityRepository = facilityRepository;
        this.facilityMapper = facilityMapper;
    }

    /**
     * Save a facility.
     *
     * @param facilityDTO the entity to save
     * @return the persisted entity
     */
    public FacilityDTO save(FacilityDTO facilityDTO) {
        log.debug("Request to save Facility : {}", facilityDTO);
        Facility facility = facilityMapper.toEntity(facilityDTO);
        facility = facilityRepository.save(facility);
        return facilityMapper.toDto(facility);
    }

    /**
     * Get all the facilities.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<FacilityDTO> findAll() {
        log.debug("Request to get all Facilities");
        return facilityRepository.findAll().stream()
            .map(facilityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one facility by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public FacilityDTO findOne(Long id) {
        log.debug("Request to get Facility : {}", id);
        Facility facility = facilityRepository.findOne(id);
        return facilityMapper.toDto(facility);
    }

    /**
     * Delete the facility by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Facility : {}", id);
        facilityRepository.delete(id);
    }
}
