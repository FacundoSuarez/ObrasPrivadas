package municipalidad.obras.web.rest;

import com.codahale.metrics.annotation.Timed;
import municipalidad.obras.domain.Oficina;
import municipalidad.obras.service.OficinaService;
import municipalidad.obras.web.rest.errors.BadRequestAlertException;
import municipalidad.obras.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Oficina.
 */
@RestController
@RequestMapping("/api")
public class OficinaResource {

    private final Logger log = LoggerFactory.getLogger(OficinaResource.class);

    private static final String ENTITY_NAME = "oficina";

    private final OficinaService oficinaService;

    public OficinaResource(OficinaService oficinaService) {
        this.oficinaService = oficinaService;
    }

    /**
     * POST  /oficinas : Create a new oficina.
     *
     * @param oficina the oficina to create
     * @return the ResponseEntity with status 201 (Created) and with body the new oficina, or with status 400 (Bad Request) if the oficina has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/oficinas")
    @Timed
    public ResponseEntity<Oficina> createOficina(@RequestBody Oficina oficina) throws URISyntaxException {
        log.debug("REST request to save Oficina : {}", oficina);
        if (oficina.getId() != null) {
            throw new BadRequestAlertException("A new oficina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Oficina result = oficinaService.save(oficina);
        return ResponseEntity.created(new URI("/api/oficinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /oficinas : Updates an existing oficina.
     *
     * @param oficina the oficina to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated oficina,
     * or with status 400 (Bad Request) if the oficina is not valid,
     * or with status 500 (Internal Server Error) if the oficina couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/oficinas")
    @Timed
    public ResponseEntity<Oficina> updateOficina(@RequestBody Oficina oficina) throws URISyntaxException {
        log.debug("REST request to update Oficina : {}", oficina);
        if (oficina.getId() == null) {
            return createOficina(oficina);
        }
        Oficina result = oficinaService.save(oficina);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, oficina.getId().toString()))
            .body(result);
    }

    /**
     * GET  /oficinas : get all the oficinas.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of oficinas in body
     */
    @GetMapping("/oficinas")
    @Timed
    public List<Oficina> getAllOficinas(@RequestParam(required = false) String filter) {
        if ("operador-is-null".equals(filter)) {
            log.debug("REST request to get all Oficinas where operador is null");
            return oficinaService.findAllWhereOperadorIsNull();
        }
        log.debug("REST request to get all Oficinas");
        return oficinaService.findAll();
        }

    /**
     * GET  /oficinas/:id : get the "id" oficina.
     *
     * @param id the id of the oficina to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the oficina, or with status 404 (Not Found)
     */
    @GetMapping("/oficinas/{id}")
    @Timed
    public ResponseEntity<Oficina> getOficina(@PathVariable Long id) {
        log.debug("REST request to get Oficina : {}", id);
        Oficina oficina = oficinaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(oficina));
    }

    /**
     * DELETE  /oficinas/:id : delete the "id" oficina.
     *
     * @param id the id of the oficina to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/oficinas/{id}")
    @Timed
    public ResponseEntity<Void> deleteOficina(@PathVariable Long id) {
        log.debug("REST request to delete Oficina : {}", id);
        oficinaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
