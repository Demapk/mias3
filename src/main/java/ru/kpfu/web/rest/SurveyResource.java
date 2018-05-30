package ru.kpfu.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.service.SurveyService;
import ru.kpfu.service.dto.SurveyDTO;
import ru.kpfu.web.rest.errors.BadRequestAlertException;
import ru.kpfu.web.rest.util.HeaderUtil;
import ru.kpfu.web.rest.util.PaginationUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/survey")
public class SurveyResource {

    private final Logger log = LoggerFactory.getLogger(SurveyResource.class);

    @Autowired
    private SurveyService surveyService;

    private static final String ENTITY_NAME = "survey";

    @PostMapping("/surveys")
    @Timed
    public ResponseEntity<SurveyDTO> createSurvey(@RequestBody SurveyDTO surveyDTO) throws URISyntaxException {
        log.debug("REST request to save Survey : {}", surveyDTO);
        if (surveyDTO.getId() != null) {
            throw new BadRequestAlertException("A new survey cannot already have an ID", ENTITY_NAME, "id exists");
        }
        SurveyDTO result = surveyService.save(surveyDTO);
        return ResponseEntity.created(new URI("/survey/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/surveys")
    @Timed
    public ResponseEntity<SurveyDTO> updateSurvey(@RequestBody SurveyDTO surveyDTO) throws URISyntaxException {
        log.debug("REST request to update Survey : {}", surveyDTO);
        if (surveyDTO.getId() == null) {
            return createSurvey(surveyDTO);
        }
        SurveyDTO result = surveyService.save(surveyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, surveyDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/surveys")
    @Timed
    public ResponseEntity<List<SurveyDTO>> getAllSurvey(Pageable pageable) {
        log.debug("REST request to get a page of Surveys");
        Page<SurveyDTO> page = surveyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/doctors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/surveys/{id}")
    @Timed
    public ResponseEntity<SurveyDTO> getSurvey(@PathVariable Long id) {
        log.debug("REST request to get Survey : {}", id);
        SurveyDTO surveyDTO = surveyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(surveyDTO));
    }

    @DeleteMapping("/surveys/{id}")
    @Timed
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
        log.debug("REST request to delete Survey : {}", id);
        surveyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
