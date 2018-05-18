package ru.kpfu.service;

import ru.kpfu.domain.Procedure;
import ru.kpfu.repository.ProcedureRepository;
import ru.kpfu.service.dto.ProcedureDTO;
import ru.kpfu.service.mapper.ProcedureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Procedure.
 */
@Service
@Transactional
public class ProcedureService {

    private final Logger log = LoggerFactory.getLogger(ProcedureService.class);

    private final ProcedureRepository procedureRepository;

    private final ProcedureMapper procedureMapper;

    public ProcedureService(ProcedureRepository procedureRepository, ProcedureMapper procedureMapper) {
        this.procedureRepository = procedureRepository;
        this.procedureMapper = procedureMapper;
    }

    /**
     * Save a procedure.
     *
     * @param procedureDTO the entity to save
     * @return the persisted entity
     */
    public ProcedureDTO save(ProcedureDTO procedureDTO) {
        log.debug("Request to save Procedure : {}", procedureDTO);
        Procedure procedure = procedureMapper.toEntity(procedureDTO);
        procedure = procedureRepository.save(procedure);
        return procedureMapper.toDto(procedure);
    }

    /**
     * Get all the procedures.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ProcedureDTO> findAll() {
        log.debug("Request to get all Procedures");
        return procedureRepository.findAll().stream()
            .map(procedureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one procedure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ProcedureDTO findOne(Long id) {
        log.debug("Request to get Procedure : {}", id);
        Procedure procedure = procedureRepository.findOne(id);
        return procedureMapper.toDto(procedure);
    }

    /**
     * Delete the procedure by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Procedure : {}", id);
        procedureRepository.delete(id);
    }
}
