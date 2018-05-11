package ru.kpfu.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.kpfu.service.HealthFacilityService;
import ru.kpfu.web.rest.errors.BadRequestAlertException;
import ru.kpfu.web.rest.util.HeaderUtil;
import ru.kpfu.service.dto.HealthFacilityDTO;
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
 * REST controller for managing HealthFacility.
 */
@RestController
@RequestMapping("/api")
public class HealthFacilityResource {

    private final Logger log = LoggerFactory.getLogger(HealthFacilityResource.class);

    private static final String ENTITY_NAME = "healthFacility";

    private final HealthFacilityService healthFacilityService;

    public HealthFacilityResource(HealthFacilityService healthFacilityService) {
        this.healthFacilityService = healthFacilityService;
    }

    /**
     * POST  /health-facilities : Create a new healthFacility.
     *
     * @param healthFacilityDTO the healthFacilityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new healthFacilityDTO, or with status 400 (Bad Request) if the healthFacility has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/health-facilities")
    @Timed
    public ResponseEntity<HealthFacilityDTO> createHealthFacility(@RequestBody HealthFacilityDTO healthFacilityDTO) throws URISyntaxException {
        log.debug("REST request to save HealthFacility : {}", healthFacilityDTO);
        if (healthFacilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new healthFacility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HealthFacilityDTO result = healthFacilityService.save(healthFacilityDTO);
        return ResponseEntity.created(new URI("/api/health-facilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /health-facilities : Updates an existing healthFacility.
     *
     * @param healthFacilityDTO the healthFacilityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated healthFacilityDTO,
     * or with status 400 (Bad Request) if the healthFacilityDTO is not valid,
     * or with status 500 (Internal Server Error) if the healthFacilityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/health-facilities")
    @Timed
    public ResponseEntity<HealthFacilityDTO> updateHealthFacility(@RequestBody HealthFacilityDTO healthFacilityDTO) throws URISyntaxException {
        log.debug("REST request to update HealthFacility : {}", healthFacilityDTO);
        if (healthFacilityDTO.getId() == null) {
            return createHealthFacility(healthFacilityDTO);
        }
        HealthFacilityDTO result = healthFacilityService.save(healthFacilityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, healthFacilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /health-facilities : get all the healthFacilities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of healthFacilities in body
     */
    @GetMapping("/health-facilities")
    @Timed
    public List<HealthFacilityDTO> getAllHealthFacilities() {
        log.debug("REST request to get all HealthFacilities");
        return healthFacilityService.findAll();
        }

    /**
     * GET  /health-facilities/:id : get the "id" healthFacility.
     *
     * @param id the id of the healthFacilityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the healthFacilityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/health-facilities/{id}")
    @Timed
    public ResponseEntity<HealthFacilityDTO> getHealthFacility(@PathVariable Long id) {
        log.debug("REST request to get HealthFacility : {}", id);
        HealthFacilityDTO healthFacilityDTO = healthFacilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(healthFacilityDTO));
    }

    /**
     * DELETE  /health-facilities/:id : delete the "id" healthFacility.
     *
     * @param id the id of the healthFacilityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/health-facilities/{id}")
    @Timed
    public ResponseEntity<Void> deleteHealthFacility(@PathVariable Long id) {
        log.debug("REST request to delete HealthFacility : {}", id);
        healthFacilityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
