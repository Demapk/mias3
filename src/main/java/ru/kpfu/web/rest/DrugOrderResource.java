package ru.kpfu.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.kpfu.service.DrugOrderService;
import ru.kpfu.web.rest.errors.BadRequestAlertException;
import ru.kpfu.web.rest.util.HeaderUtil;
import ru.kpfu.service.dto.DrugOrderDTO;
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
 * REST controller for managing DrugOrder.
 */
@RestController
@RequestMapping("/api")
public class DrugOrderResource {

    private final Logger log = LoggerFactory.getLogger(DrugOrderResource.class);

    private static final String ENTITY_NAME = "drugOrder";

    private final DrugOrderService drugOrderService;

    public DrugOrderResource(DrugOrderService drugOrderService) {
        this.drugOrderService = drugOrderService;
    }

    /**
     * POST  /drug-orders : Create a new drugOrder.
     *
     * @param drugOrderDTO the drugOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new drugOrderDTO, or with status 400 (Bad Request) if the drugOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/drug-orders")
    @Timed
    public ResponseEntity<DrugOrderDTO> createDrugOrder(@RequestBody DrugOrderDTO drugOrderDTO) throws URISyntaxException {
        log.debug("REST request to save DrugOrder : {}", drugOrderDTO);
        if (drugOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new drugOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DrugOrderDTO result = drugOrderService.save(drugOrderDTO);
        return ResponseEntity.created(new URI("/api/drug-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drug-orders : Updates an existing drugOrder.
     *
     * @param drugOrderDTO the drugOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated drugOrderDTO,
     * or with status 400 (Bad Request) if the drugOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the drugOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/drug-orders")
    @Timed
    public ResponseEntity<DrugOrderDTO> updateDrugOrder(@RequestBody DrugOrderDTO drugOrderDTO) throws URISyntaxException {
        log.debug("REST request to update DrugOrder : {}", drugOrderDTO);
        if (drugOrderDTO.getId() == null) {
            return createDrugOrder(drugOrderDTO);
        }
        DrugOrderDTO result = drugOrderService.save(drugOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, drugOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drug-orders : get all the drugOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of drugOrders in body
     */
    @GetMapping("/drug-orders")
    @Timed
    public List<DrugOrderDTO> getAllDrugOrders() {
        log.debug("REST request to get all DrugOrders");
        return drugOrderService.findAll();
        }

    /**
     * GET  /drug-orders/:id : get the "id" drugOrder.
     *
     * @param id the id of the drugOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the drugOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/drug-orders/{id}")
    @Timed
    public ResponseEntity<DrugOrderDTO> getDrugOrder(@PathVariable Long id) {
        log.debug("REST request to get DrugOrder : {}", id);
        DrugOrderDTO drugOrderDTO = drugOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(drugOrderDTO));
    }

    /**
     * DELETE  /drug-orders/:id : delete the "id" drugOrder.
     *
     * @param id the id of the drugOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/drug-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteDrugOrder(@PathVariable Long id) {
        log.debug("REST request to delete DrugOrder : {}", id);
        drugOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
