package ru.kpfu.service;

import ru.kpfu.domain.Diagnosis;
import ru.kpfu.repository.DiagnosisRepository;
import ru.kpfu.service.dto.DiagnosisDTO;
import ru.kpfu.service.mapper.DiagnosisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Diagnosis.
 */
@Service
@Transactional
public class DiagnosisService {

    private final Logger log = LoggerFactory.getLogger(DiagnosisService.class);

    private final DiagnosisRepository diagnosisRepository;

    private final DiagnosisMapper diagnosisMapper;

    public DiagnosisService(DiagnosisRepository diagnosisRepository, DiagnosisMapper diagnosisMapper) {
        this.diagnosisRepository = diagnosisRepository;
        this.diagnosisMapper = diagnosisMapper;
    }

    /**
     * Save a diagnosis.
     *
     * @param diagnosisDTO the entity to save
     * @return the persisted entity
     */
    public DiagnosisDTO save(DiagnosisDTO diagnosisDTO) {
        log.debug("Request to save Diagnosis : {}", diagnosisDTO);
        Diagnosis diagnosis = diagnosisMapper.toEntity(diagnosisDTO);
        diagnosis = diagnosisRepository.save(diagnosis);
        return diagnosisMapper.toDto(diagnosis);
    }

    /**
     * Get all the diagnoses.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DiagnosisDTO> findAll() {
        log.debug("Request to get all Diagnoses");
        return diagnosisRepository.findAll().stream()
            .map(diagnosisMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one diagnosis by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DiagnosisDTO findOne(Long id) {
        log.debug("Request to get Diagnosis : {}", id);
        Diagnosis diagnosis = diagnosisRepository.findOne(id);
        return diagnosisMapper.toDto(diagnosis);
    }

    /**
     * Delete the diagnosis by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Diagnosis : {}", id);
        diagnosisRepository.delete(id);
    }
}
