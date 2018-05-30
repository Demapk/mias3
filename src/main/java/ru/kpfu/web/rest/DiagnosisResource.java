package ru.kpfu.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.kpfu.service.DiagnosisService;
import ru.kpfu.web.rest.errors.BadRequestAlertException;
import ru.kpfu.web.rest.util.HeaderUtil;
import ru.kpfu.service.dto.DiagnosisDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Diagnosis.
 */
@RestController
@RequestMapping("/api")
public class DiagnosisResource {

    private final Logger log = LoggerFactory.getLogger(DiagnosisResource.class);

    private static final String ENTITY_NAME = "diagnosis";

    private final DiagnosisService diagnosisService;

    public DiagnosisResource(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }

    /**
     * POST  /diagnoses : Create a new diagnosis.
     *
     * @param diagnosisDTO the diagnosisDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new diagnosisDTO, or with status 400 (Bad Request) if the diagnosis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/diagnoses")
    @Timed
    public ResponseEntity<DiagnosisDTO> createDiagnosis(@RequestBody DiagnosisDTO diagnosisDTO) throws URISyntaxException {
        log.debug("REST request to save Diagnosis : {}", diagnosisDTO);
        if (diagnosisDTO.getId() != null) {
            throw new BadRequestAlertException("A new diagnosis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiagnosisDTO result = diagnosisService.save(diagnosisDTO);
        return ResponseEntity.created(new URI("/api/diagnoses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /diagnoses : Updates an existing diagnosis.
     *
     * @param diagnosisDTO the diagnosisDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated diagnosisDTO,
     * or with status 400 (Bad Request) if the diagnosisDTO is not valid,
     * or with status 500 (Internal Server Error) if the diagnosisDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/diagnoses")
    @Timed
    public ResponseEntity<DiagnosisDTO> updateDiagnosis(@RequestBody DiagnosisDTO diagnosisDTO) throws URISyntaxException {
        log.debug("REST request to update Diagnosis : {}", diagnosisDTO);
        if (diagnosisDTO.getId() == null) {
            return createDiagnosis(diagnosisDTO);
        }
        DiagnosisDTO result = diagnosisService.save(diagnosisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, diagnosisDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /diagnoses : get all the diagnoses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of diagnoses in body
     */
    @GetMapping("/diagnoses")
    @Timed
    public List<DiagnosisDTO> getAllDiagnoses() {
        log.debug("REST request to get all Diagnoses");
        return diagnosisService.findAll();
        }

    /**
     * GET  /diagnoses/:id : get the "id" diagnosis.
     *
     * @param id the id of the diagnosisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the diagnosisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/diagnoses/{id}")
    @Timed
    public ResponseEntity<DiagnosisDTO> getDiagnosis(@PathVariable Long id) {
        log.debug("REST request to get Diagnosis : {}", id);
        DiagnosisDTO diagnosisDTO = diagnosisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(diagnosisDTO));
    }

    /**
     * DELETE  /diagnoses/:id : delete the "id" diagnosis.
     *
     * @param id the id of the diagnosisDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/diagnoses/{id}")
    @Timed
    public ResponseEntity<Void> deleteDiagnosis(@PathVariable Long id) {
        log.debug("REST request to delete Diagnosis : {}", id);
        diagnosisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
