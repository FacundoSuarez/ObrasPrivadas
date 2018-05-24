package municipalidad.obras.web.rest;

import com.codahale.metrics.annotation.Timed;
import municipalidad.obras.domain.PlanoDetalle;
import municipalidad.obras.service.PlanoDetalleService;
import municipalidad.obras.web.rest.errors.BadRequestAlertException;
import municipalidad.obras.web.rest.util.HeaderUtil;
import municipalidad.obras.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PlanoDetalle.
 */
@RestController
@RequestMapping("/api")
public class PlanoDetalleResource {

    private final Logger log = LoggerFactory.getLogger(PlanoDetalleResource.class);

    private static final String ENTITY_NAME = "planoDetalle";

    private final PlanoDetalleService planoDetalleService;

    public PlanoDetalleResource(PlanoDetalleService planoDetalleService) {
        this.planoDetalleService = planoDetalleService;
    }

    /**
     * POST  /plano-detalles : Create a new planoDetalle.
     *
     * @param planoDetalle the planoDetalle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new planoDetalle, or with status 400 (Bad Request) if the planoDetalle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plano-detalles")
    @Timed
    public ResponseEntity<PlanoDetalle> createPlanoDetalle(@RequestBody PlanoDetalle planoDetalle) throws URISyntaxException {
        log.debug("REST request to save PlanoDetalle : {}", planoDetalle);
        if (planoDetalle.getId() != null) {
            throw new BadRequestAlertException("A new planoDetalle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanoDetalle result = planoDetalleService.save(planoDetalle);
        return ResponseEntity.created(new URI("/api/plano-detalles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /plano-detalles : Updates an existing planoDetalle.
     *
     * @param planoDetalle the planoDetalle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated planoDetalle,
     * or with status 400 (Bad Request) if the planoDetalle is not valid,
     * or with status 500 (Internal Server Error) if the planoDetalle couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plano-detalles")
    @Timed
    public ResponseEntity<PlanoDetalle> updatePlanoDetalle(@RequestBody PlanoDetalle planoDetalle) throws URISyntaxException {
        log.debug("REST request to update PlanoDetalle : {}", planoDetalle);
        if (planoDetalle.getId() == null) {
            return createPlanoDetalle(planoDetalle);
        }
        PlanoDetalle result = planoDetalleService.save(planoDetalle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, planoDetalle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plano-detalles : get all the planoDetalles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of planoDetalles in body
     */
    @GetMapping("/plano-detalles")
    @Timed
    public ResponseEntity<List<PlanoDetalle>> getAllPlanoDetalles(Pageable pageable) {
        log.debug("REST request to get a page of PlanoDetalles");
        Page<PlanoDetalle> page = planoDetalleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/plano-detalles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /plano-detalles/:id : get the "id" planoDetalle.
     *
     * @param id the id of the planoDetalle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the planoDetalle, or with status 404 (Not Found)
     */
    @GetMapping("/plano-detalles/{id}")
    @Timed
    public ResponseEntity<PlanoDetalle> getPlanoDetalle(@PathVariable Long id) {
        log.debug("REST request to get PlanoDetalle : {}", id);
        PlanoDetalle planoDetalle = planoDetalleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(planoDetalle));
    }

    /**
     * DELETE  /plano-detalles/:id : delete the "id" planoDetalle.
     *
     * @param id the id of the planoDetalle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plano-detalles/{id}")
    @Timed
    public ResponseEntity<Void> deletePlanoDetalle(@PathVariable Long id) {
        log.debug("REST request to delete PlanoDetalle : {}", id);
        planoDetalleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
