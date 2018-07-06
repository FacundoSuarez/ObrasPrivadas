package municipalidad.obras.web.rest;

import com.codahale.metrics.annotation.Timed;
import municipalidad.obras.domain.Tramite;
import municipalidad.obras.service.TramiteService;
import municipalidad.obras.web.rest.errors.BadRequestAlertException;
import municipalidad.obras.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import municipalidad.obras.service.dto.TramiteDTO;

/**
 * REST controller for managing Tramite.
 */
@RestController
@RequestMapping("/api")
public class TramiteResource {

    private final Logger log = LoggerFactory.getLogger(TramiteResource.class);

    private static final String ENTITY_NAME = "tramite";

    private final TramiteService tramiteService;

    public TramiteResource(TramiteService tramiteService) {
        this.tramiteService = tramiteService;
    }

    /**
     * POST  /tramites : Create a new tramite.
     *
     * @param tramite the tramite to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tramite, or with status 400 (Bad Request) if the tramite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tramites")
    @Timed
    public ResponseEntity<Tramite> createTramite(@RequestBody Tramite tramite) throws URISyntaxException {
        log.debug("REST request to save Tramite : {}", tramite);
        if (tramite.getId() != null) {
            throw new BadRequestAlertException("A new tramite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tramite result = tramiteService.save(tramite);
        return ResponseEntity.created(new URI("/api/tramites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tramites : Updates an existing tramite.
     *
     * @param tramite the tramite to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tramite,
     * or with status 400 (Bad Request) if the tramite is not valid,
     * or with status 500 (Internal Server Error) if the tramite couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tramites")
    @Timed
    public ResponseEntity<Tramite> updateTramite(@RequestBody Tramite tramite) throws URISyntaxException {
        log.debug("REST request to update Tramite : {}", tramite);
        if (tramite.getId() == null) {
            return createTramite(tramite);
        }
        Tramite result = tramiteService.save(tramite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tramite.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tramites : get all the tramites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tramites in body
     */
    @GetMapping("/tramites")
    @Timed
    public List<TramiteDTO> getAllTramites() {
        log.debug("REST request to get all Tramites");
        return tramiteService.findAll();
        }

    /**
     * GET  /tramites/:id : get the "id" tramite.
     *
     * @param id the id of the tramite to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tramite, or with status 404 (Not Found)
     */
    @GetMapping("/tramites/{id}")
    @Timed
    public ResponseEntity<Tramite> getTramite(@PathVariable Long id) {
        log.debug("REST request to get Tramite : {}", id);
        Tramite tramite = tramiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tramite));
    }

    /**
     * DELETE  /tramites/:id : delete the "id" tramite.
     *
     * @param id the id of the tramite to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tramites/{id}")
    @Timed
    public ResponseEntity<Void> deleteTramite(@PathVariable Long id) {
        log.debug("REST request to delete Tramite : {}", id);
        tramiteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
