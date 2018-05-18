package ru.kpfu.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.kpfu.service.DrugService;
import ru.kpfu.web.rest.errors.BadRequestAlertException;
import ru.kpfu.web.rest.util.HeaderUtil;
import ru.kpfu.service.dto.DrugDTO;
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
 * REST controller for managing Drug.
 */
@RestController
@RequestMapping("/api")
public class DrugResource {

    private final Logger log = LoggerFactory.getLogger(DrugResource.class);

    private static final String ENTITY_NAME = "drug";

    private final DrugService drugService;

    public DrugResource(DrugService drugService) {
        this.drugService = drugService;
    }

    /**
     * POST  /drugs : Create a new drug.
     *
     * @param drugDTO the drugDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new drugDTO, or with status 400 (Bad Request) if the drug has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/drugs")
    @Timed
    public ResponseEntity<DrugDTO> createDrug(@RequestBody DrugDTO drugDTO) throws URISyntaxException {
        log.debug("REST request to save Drug : {}", drugDTO);
        if (drugDTO.getId() != null) {
            throw new BadRequestAlertException("A new drug cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DrugDTO result = drugService.save(drugDTO);
        return ResponseEntity.created(new URI("/api/drugs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drugs : Updates an existing drug.
     *
     * @param drugDTO the drugDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated drugDTO,
     * or with status 400 (Bad Request) if the drugDTO is not valid,
     * or with status 500 (Internal Server Error) if the drugDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/drugs")
    @Timed
    public ResponseEntity<DrugDTO> updateDrug(@RequestBody DrugDTO drugDTO) throws URISyntaxException {
        log.debug("REST request to update Drug : {}", drugDTO);
        if (drugDTO.getId() == null) {
            return createDrug(drugDTO);
        }
        DrugDTO result = drugService.save(drugDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, drugDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drugs : get all the drugs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of drugs in body
     */
    @GetMapping("/drugs")
    @Timed
    public List<DrugDTO> getAllDrugs() {
        log.debug("REST request to get all Drugs");
        return drugService.findAll();
        }

    /**
     * GET  /drugs/:id : get the "id" drug.
     *
     * @param id the id of the drugDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the drugDTO, or with status 404 (Not Found)
     */
    @GetMapping("/drugs/{id}")
    @Timed
    public ResponseEntity<DrugDTO> getDrug(@PathVariable Long id) {
        log.debug("REST request to get Drug : {}", id);
        DrugDTO drugDTO = drugService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(drugDTO));
    }

    /**
     * DELETE  /drugs/:id : delete the "id" drug.
     *
     * @param id the id of the drugDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/drugs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDrug(@PathVariable Long id) {
        log.debug("REST request to delete Drug : {}", id);
        drugService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
