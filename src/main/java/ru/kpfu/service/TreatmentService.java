package ru.kpfu.service;

import ru.kpfu.domain.Treatment;
import ru.kpfu.repository.TreatmentRepository;
import ru.kpfu.service.dto.TreatmentDTO;
import ru.kpfu.service.mapper.TreatmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Treatment.
 */
@Service
@Transactional
public class TreatmentService {

    private final Logger log = LoggerFactory.getLogger(TreatmentService.class);

    private final TreatmentRepository treatmentRepository;

    private final TreatmentMapper treatmentMapper;

    public TreatmentService(TreatmentRepository treatmentRepository, TreatmentMapper treatmentMapper) {
        this.treatmentRepository = treatmentRepository;
        this.treatmentMapper = treatmentMapper;
    }

    /**
     * Save a treatment.
     *
     * @param treatmentDTO the entity to save
     * @return the persisted entity
     */
    public TreatmentDTO save(TreatmentDTO treatmentDTO) {
        log.debug("Request to save Treatment : {}", treatmentDTO);
        Treatment treatment = treatmentMapper.toEntity(treatmentDTO);
        treatment = treatmentRepository.save(treatment);
        return treatmentMapper.toDto(treatment);
    }

    /**
     * Get all the treatments.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TreatmentDTO> findAll() {
        log.debug("Request to get all Treatments");
        return treatmentRepository.findAll().stream()
            .map(treatmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one treatment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TreatmentDTO findOne(Long id) {
        log.debug("Request to get Treatment : {}", id);
        Treatment treatment = treatmentRepository.findOne(id);
        return treatmentMapper.toDto(treatment);
    }

    /**
     * Delete the treatment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Treatment : {}", id);
        treatmentRepository.delete(id);
    }
}
