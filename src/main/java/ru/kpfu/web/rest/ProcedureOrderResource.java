package ru.kpfu.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.kpfu.service.ProcedureOrderService;
import ru.kpfu.web.rest.errors.BadRequestAlertException;
import ru.kpfu.web.rest.util.HeaderUtil;
import ru.kpfu.service.dto.ProcedureOrderDTO;
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
 * REST controller for managing ProcedureOrder.
 */
@RestController
@RequestMapping("/api")
public class ProcedureOrderResource {

    private final Logger log = LoggerFactory.getLogger(ProcedureOrderResource.class);

    private static final String ENTITY_NAME = "procedureOrder";

    private final ProcedureOrderService procedureOrderService;

    public ProcedureOrderResource(ProcedureOrderService procedureOrderService) {
        this.procedureOrderService = procedureOrderService;
    }

    /**
     * POST  /procedure-orders : Create a new procedureOrder.
     *
     * @param procedureOrderDTO the procedureOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new procedureOrderDTO, or with status 400 (Bad Request) if the procedureOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/procedure-orders")
    @Timed
    public ResponseEntity<ProcedureOrderDTO> createProcedureOrder(@RequestBody ProcedureOrderDTO procedureOrderDTO) throws URISyntaxException {
        log.debug("REST request to save ProcedureOrder : {}", procedureOrderDTO);
        if (procedureOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new procedureOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcedureOrderDTO result = procedureOrderService.save(procedureOrderDTO);
        return ResponseEntity.created(new URI("/api/procedure-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /procedure-orders : Updates an existing procedureOrder.
     *
     * @param procedureOrderDTO the procedureOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated procedureOrderDTO,
     * or with status 400 (Bad Request) if the procedureOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the procedureOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/procedure-orders")
    @Timed
    public ResponseEntity<ProcedureOrderDTO> updateProcedureOrder(@RequestBody ProcedureOrderDTO procedureOrderDTO) throws URISyntaxException {
        log.debug("REST request to update ProcedureOrder : {}", procedureOrderDTO);
        if (procedureOrderDTO.getId() == null) {
            return createProcedureOrder(procedureOrderDTO);
        }
        ProcedureOrderDTO result = procedureOrderService.save(procedureOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, procedureOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /procedure-orders : get all the procedureOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of procedureOrders in body
     */
    @GetMapping("/procedure-orders")
    @Timed
    public List<ProcedureOrderDTO> getAllProcedureOrders() {
        log.debug("REST request to get all ProcedureOrders");
        return procedureOrderService.findAll();
        }

    /**
     * GET  /procedure-orders/:id : get the "id" procedureOrder.
     *
     * @param id the id of the procedureOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the procedureOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/procedure-orders/{id}")
    @Timed
    public ResponseEntity<ProcedureOrderDTO> getProcedureOrder(@PathVariable Long id) {
        log.debug("REST request to get ProcedureOrder : {}", id);
        ProcedureOrderDTO procedureOrderDTO = procedureOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(procedureOrderDTO));
    }

    /**
     * DELETE  /procedure-orders/:id : delete the "id" procedureOrder.
     *
     * @param id the id of the procedureOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/procedure-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteProcedureOrder(@PathVariable Long id) {
        log.debug("REST request to delete ProcedureOrder : {}", id);
        procedureOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
