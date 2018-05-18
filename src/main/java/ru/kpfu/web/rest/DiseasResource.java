package ru.kpfu.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.kpfu.service.DiseasService;
import ru.kpfu.web.rest.errors.BadRequestAlertException;
import ru.kpfu.web.rest.util.HeaderUtil;
import ru.kpfu.service.dto.DiseasDTO;
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
 * REST controller for managing Diseas.
 */
@RestController
@RequestMapping("/api")
public class DiseasResource {

    private final Logger log = LoggerFactory.getLogger(DiseasResource.class);

    private static final String ENTITY_NAME = "diseas";

    private final DiseasService diseasService;

    public DiseasResource(DiseasService diseasService) {
        this.diseasService = diseasService;
    }

    /**
     * POST  /diseas : Create a new diseas.
     *
     * @param diseasDTO the diseasDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new diseasDTO, or with status 400 (Bad Request) if the diseas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/diseas")
    @Timed
    public ResponseEntity<DiseasDTO> createDiseas(@Valid @RequestBody DiseasDTO diseasDTO) throws URISyntaxException {
        log.debug("REST request to save Diseas : {}", diseasDTO);
        if (diseasDTO.getId() != null) {
            throw new BadRequestAlertException("A new diseas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiseasDTO result = diseasService.save(diseasDTO);
        return ResponseEntity.created(new URI("/api/diseas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /diseas : Updates an existing diseas.
     *
     * @param diseasDTO the diseasDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated diseasDTO,
     * or with status 400 (Bad Request) if the diseasDTO is not valid,
     * or with status 500 (Internal Server Error) if the diseasDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/diseas")
    @Timed
    public ResponseEntity<DiseasDTO> updateDiseas(@Valid @RequestBody DiseasDTO diseasDTO) throws URISyntaxException {
        log.debug("REST request to update Diseas : {}", diseasDTO);
        if (diseasDTO.getId() == null) {
            return createDiseas(diseasDTO);
        }
        DiseasDTO result = diseasService.save(diseasDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, diseasDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /diseas : get all the diseas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of diseas in body
     */
    @GetMapping("/diseas")
    @Timed
    public List<DiseasDTO> getAllDiseas() {
        log.debug("REST request to get all Diseas");
        return diseasService.findAll();
        }

    /**
     * GET  /diseas/:id : get the "id" diseas.
     *
     * @param id the id of the diseasDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the diseasDTO, or with status 404 (Not Found)
     */
    @GetMapping("/diseas/{id}")
    @Timed
    public ResponseEntity<DiseasDTO> getDiseas(@PathVariable Long id) {
        log.debug("REST request to get Diseas : {}", id);
        DiseasDTO diseasDTO = diseasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(diseasDTO));
    }

    /**
     * DELETE  /diseas/:id : delete the "id" diseas.
     *
     * @param id the id of the diseasDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/diseas/{id}")
    @Timed
    public ResponseEntity<Void> deleteDiseas(@PathVariable Long id) {
        log.debug("REST request to delete Diseas : {}", id);
        diseasService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
