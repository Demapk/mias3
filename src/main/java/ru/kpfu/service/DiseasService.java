package ru.kpfu.service;

import ru.kpfu.domain.Diseas;
import ru.kpfu.repository.DiseasRepository;
import ru.kpfu.service.dto.DiseasDTO;
import ru.kpfu.service.mapper.DiseasMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Diseas.
 */
@Service
@Transactional
public class DiseasService {

    private final Logger log = LoggerFactory.getLogger(DiseasService.class);

    private final DiseasRepository diseasRepository;

    private final DiseasMapper diseasMapper;

    public DiseasService(DiseasRepository diseasRepository, DiseasMapper diseasMapper) {
        this.diseasRepository = diseasRepository;
        this.diseasMapper = diseasMapper;
    }

    /**
     * Save a diseas.
     *
     * @param diseasDTO the entity to save
     * @return the persisted entity
     */
    public DiseasDTO save(DiseasDTO diseasDTO) {
        log.debug("Request to save Diseas : {}", diseasDTO);
        Diseas diseas = diseasMapper.toEntity(diseasDTO);
        diseas = diseasRepository.save(diseas);
        return diseasMapper.toDto(diseas);
    }

    /**
     * Get all the diseas.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DiseasDTO> findAll() {
        log.debug("Request to get all Diseas");
        return diseasRepository.findAll().stream()
            .map(diseasMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one diseas by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DiseasDTO findOne(Long id) {
        log.debug("Request to get Diseas : {}", id);
        Diseas diseas = diseasRepository.findOne(id);
        return diseasMapper.toDto(diseas);
    }

    /**
     * Delete the diseas by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Diseas : {}", id);
        diseasRepository.delete(id);
    }
}
