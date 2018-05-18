package ru.kpfu.service;

import ru.kpfu.domain.Speciality;
import ru.kpfu.repository.SpecialityRepository;
import ru.kpfu.service.dto.SpecialityDTO;
import ru.kpfu.service.mapper.SpecialityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Speciality.
 */
@Service
@Transactional
public class SpecialityService {

    private final Logger log = LoggerFactory.getLogger(SpecialityService.class);

    private final SpecialityRepository specialityRepository;

    private final SpecialityMapper specialityMapper;

    public SpecialityService(SpecialityRepository specialityRepository, SpecialityMapper specialityMapper) {
        this.specialityRepository = specialityRepository;
        this.specialityMapper = specialityMapper;
    }

    /**
     * Save a speciality.
     *
     * @param specialityDTO the entity to save
     * @return the persisted entity
     */
    public SpecialityDTO save(SpecialityDTO specialityDTO) {
        log.debug("Request to save Speciality : {}", specialityDTO);
        Speciality speciality = specialityMapper.toEntity(specialityDTO);
        speciality = specialityRepository.save(speciality);
        return specialityMapper.toDto(speciality);
    }

    /**
     * Get all the specialities.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SpecialityDTO> findAll() {
        log.debug("Request to get all Specialities");
        return specialityRepository.findAll().stream()
            .map(specialityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one speciality by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SpecialityDTO findOne(Long id) {
        log.debug("Request to get Speciality : {}", id);
        Speciality speciality = specialityRepository.findOne(id);
        return specialityMapper.toDto(speciality);
    }

    /**
     * Delete the speciality by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Speciality : {}", id);
        specialityRepository.delete(id);
    }
}
