package ru.kpfu.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.kpfu.service.SpecialityService;
import ru.kpfu.web.rest.errors.BadRequestAlertException;
import ru.kpfu.web.rest.util.HeaderUtil;
import ru.kpfu.service.dto.SpecialityDTO;
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
 * REST controller for managing Speciality.
 */
@RestController
@RequestMapping("/api")
public class SpecialityResource {

    private final Logger log = LoggerFactory.getLogger(SpecialityResource.class);

    private static final String ENTITY_NAME = "speciality";

    private final SpecialityService specialityService;

    public SpecialityResource(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    /**
     * POST  /specialities : Create a new speciality.
     *
     * @param specialityDTO the specialityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new specialityDTO, or with status 400 (Bad Request) if the speciality has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/specialities")
    @Timed
    public ResponseEntity<SpecialityDTO> createSpeciality(@RequestBody SpecialityDTO specialityDTO) throws URISyntaxException {
        log.debug("REST request to save Speciality : {}", specialityDTO);
        if (specialityDTO.getId() != null) {
            throw new BadRequestAlertException("A new speciality cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpecialityDTO result = specialityService.save(specialityDTO);
        return ResponseEntity.created(new URI("/api/specialities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /specialities : Updates an existing speciality.
     *
     * @param specialityDTO the specialityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated specialityDTO,
     * or with status 400 (Bad Request) if the specialityDTO is not valid,
     * or with status 500 (Internal Server Error) if the specialityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/specialities")
    @Timed
    public ResponseEntity<SpecialityDTO> updateSpeciality(@RequestBody SpecialityDTO specialityDTO) throws URISyntaxException {
        log.debug("REST request to update Speciality : {}", specialityDTO);
        if (specialityDTO.getId() == null) {
            return createSpeciality(specialityDTO);
        }
        SpecialityDTO result = specialityService.save(specialityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, specialityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /specialities : get all the specialities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of specialities in body
     */
    @GetMapping("/specialities")
    @Timed
    public List<SpecialityDTO> getAllSpecialities() {
        log.debug("REST request to get all Specialities");
        return specialityService.findAll();
        }

    /**
     * GET  /specialities/:id : get the "id" speciality.
     *
     * @param id the id of the specialityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the specialityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/specialities/{id}")
    @Timed
    public ResponseEntity<SpecialityDTO> getSpeciality(@PathVariable Long id) {
        log.debug("REST request to get Speciality : {}", id);
        SpecialityDTO specialityDTO = specialityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(specialityDTO));
    }

    /**
     * DELETE  /specialities/:id : delete the "id" speciality.
     *
     * @param id the id of the specialityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/specialities/{id}")
    @Timed
    public ResponseEntity<Void> deleteSpeciality(@PathVariable Long id) {
        log.debug("REST request to delete Speciality : {}", id);
        specialityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
