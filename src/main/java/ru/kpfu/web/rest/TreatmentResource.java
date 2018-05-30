package ru.kpfu.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.kpfu.service.TreatmentService;
import ru.kpfu.web.rest.errors.BadRequestAlertException;
import ru.kpfu.web.rest.util.HeaderUtil;
import ru.kpfu.service.dto.TreatmentDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Treatment.
 */
@RestController
@RequestMapping("/api")
public class TreatmentResource {

    private final Logger log = LoggerFactory.getLogger(TreatmentResource.class);

    private static final String ENTITY_NAME = "treatment";

    private final TreatmentService treatmentService;

    public TreatmentResource(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    /**
     * POST  /treatments : Create a new treatment.
     *
     * @param treatmentDTO the treatmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new treatmentDTO, or with status 400 (Bad Request) if the treatment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/treatments")
    @Timed
    public ResponseEntity<TreatmentDTO> createTreatment(@Valid @RequestBody TreatmentDTO treatmentDTO) throws URISyntaxException {
        log.debug("REST request to save Treatment : {}", treatmentDTO);
        if (treatmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new treatment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TreatmentDTO result = treatmentService.save(treatmentDTO);
        return ResponseEntity.created(new URI("/api/treatments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /treatments : Updates an existing treatment.
     *
     * @param treatmentDTO the treatmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated treatmentDTO,
     * or with status 400 (Bad Request) if the treatmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the treatmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/treatments")
    @Timed
    public ResponseEntity<TreatmentDTO> updateTreatment(@Valid @RequestBody TreatmentDTO treatmentDTO) throws URISyntaxException {
        log.debug("REST request to update Treatment : {}", treatmentDTO);
        if (treatmentDTO.getId() == null) {
            return createTreatment(treatmentDTO);
        }
        TreatmentDTO result = treatmentService.save(treatmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, treatmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /treatments : get all the treatments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of treatments in body
     */
    @GetMapping("/treatments")
    @Timed
    public List<TreatmentDTO> getAllTreatments() {
        log.debug("REST request to get all Treatments");
        return treatmentService.findAll();
        }

    /**
     * GET  /treatments/:id : get the "id" treatment.
     *
     * @param id the id of the treatmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the treatmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/treatments/{id}")
    @Timed
    public ResponseEntity<TreatmentDTO> getTreatment(@PathVariable Long id) {
        log.debug("REST request to get Treatment : {}", id);
        TreatmentDTO treatmentDTO = treatmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(treatmentDTO));
    }

    /**
     * DELETE  /treatments/:id : delete the "id" treatment.
     *
     * @param id the id of the treatmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/treatments/{id}")
    @Timed
    public ResponseEntity<Void> deleteTreatment(@PathVariable Long id) {
        log.debug("REST request to delete Treatment : {}", id);
        treatmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
