package ru.kpfu.service;

import ru.kpfu.domain.Prescription;
import ru.kpfu.repository.PrescriptionRepository;
import ru.kpfu.service.dto.PrescriptionDTO;
import ru.kpfu.service.mapper.PrescriptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Prescription.
 */
@Service
@Transactional
public class PrescriptionService {

    private final Logger log = LoggerFactory.getLogger(PrescriptionService.class);

    private final PrescriptionRepository prescriptionRepository;

    private final PrescriptionMapper prescriptionMapper;

    public PrescriptionService(PrescriptionRepository prescriptionRepository, PrescriptionMapper prescriptionMapper) {
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionMapper = prescriptionMapper;
    }

    /**
     * Save a prescription.
     *
     * @param prescriptionDTO the entity to save
     * @return the persisted entity
     */
    public PrescriptionDTO save(PrescriptionDTO prescriptionDTO) {
        log.debug("Request to save Prescription : {}", prescriptionDTO);
        Prescription prescription = prescriptionMapper.toEntity(prescriptionDTO);
        prescription = prescriptionRepository.save(prescription);
        return prescriptionMapper.toDto(prescription);
    }

    /**
     * Get all the prescriptions.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PrescriptionDTO> findAll() {
        log.debug("Request to get all Prescriptions");
        return prescriptionRepository.findAll().stream()
            .map(prescriptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one prescription by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PrescriptionDTO findOne(Long id) {
        log.debug("Request to get Prescription : {}", id);
        Prescription prescription = prescriptionRepository.findOne(id);
        return prescriptionMapper.toDto(prescription);
    }

    /**
     * Delete the prescription by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Prescription : {}", id);
        prescriptionRepository.delete(id);
    }
}
