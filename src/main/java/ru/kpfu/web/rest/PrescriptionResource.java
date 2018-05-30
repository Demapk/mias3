package ru.kpfu.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.kpfu.service.PrescriptionService;
import ru.kpfu.web.rest.errors.BadRequestAlertException;
import ru.kpfu.web.rest.util.HeaderUtil;
import ru.kpfu.service.dto.PrescriptionDTO;
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
 * REST controller for managing Prescription.
 */
@RestController
@RequestMapping("/api")
public class PrescriptionResource {

    private final Logger log = LoggerFactory.getLogger(PrescriptionResource.class);

    private static final String ENTITY_NAME = "prescription";

    private final PrescriptionService prescriptionService;

    public PrescriptionResource(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    /**
     * POST  /prescriptions : Create a new prescription.
     *
     * @param prescriptionDTO the prescriptionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prescriptionDTO, or with status 400 (Bad Request) if the prescription has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prescriptions")
    @Timed
    public ResponseEntity<PrescriptionDTO> createPrescription(@RequestBody PrescriptionDTO prescriptionDTO) throws URISyntaxException {
        log.debug("REST request to save Prescription : {}", prescriptionDTO);
        if (prescriptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new prescription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrescriptionDTO result = prescriptionService.save(prescriptionDTO);
        return ResponseEntity.created(new URI("/api/prescriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prescriptions : Updates an existing prescription.
     *
     * @param prescriptionDTO the prescriptionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prescriptionDTO,
     * or with status 400 (Bad Request) if the prescriptionDTO is not valid,
     * or with status 500 (Internal Server Error) if the prescriptionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prescriptions")
    @Timed
    public ResponseEntity<PrescriptionDTO> updatePrescription(@RequestBody PrescriptionDTO prescriptionDTO) throws URISyntaxException {
        log.debug("REST request to update Prescription : {}", prescriptionDTO);
        if (prescriptionDTO.getId() == null) {
            return createPrescription(prescriptionDTO);
        }
        PrescriptionDTO result = prescriptionService.save(prescriptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prescriptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prescriptions : get all the prescriptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of prescriptions in body
     */
    @GetMapping("/prescriptions")
    @Timed
    public List<PrescriptionDTO> getAllPrescriptions() {
        log.debug("REST request to get all Prescriptions");
        return prescriptionService.findAll();
        }

    /**
     * GET  /prescriptions/:id : get the "id" prescription.
     *
     * @param id the id of the prescriptionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prescriptionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/prescriptions/{id}")
    @Timed
    public ResponseEntity<PrescriptionDTO> getPrescription(@PathVariable Long id) {
        log.debug("REST request to get Prescription : {}", id);
        PrescriptionDTO prescriptionDTO = prescriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(prescriptionDTO));
    }

    /**
     * DELETE  /prescriptions/:id : delete the "id" prescription.
     *
     * @param id the id of the prescriptionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prescriptions/{id}")
    @Timed
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        log.debug("REST request to delete Prescription : {}", id);
        prescriptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
