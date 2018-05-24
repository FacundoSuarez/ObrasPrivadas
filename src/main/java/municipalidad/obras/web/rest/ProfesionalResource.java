package municipalidad.obras.web.rest;

import com.codahale.metrics.annotation.Timed;
import municipalidad.obras.domain.Profesional;
import municipalidad.obras.service.ProfesionalService;
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

/**
 * REST controller for managing Profesional.
 */
@RestController
@RequestMapping("/api")
public class ProfesionalResource {

    private final Logger log = LoggerFactory.getLogger(ProfesionalResource.class);

    private static final String ENTITY_NAME = "profesional";

    private final ProfesionalService profesionalService;

    public ProfesionalResource(ProfesionalService profesionalService) {
        this.profesionalService = profesionalService;
    }

    /**
     * POST  /profesionals : Create a new profesional.
     *
     * @param profesional the profesional to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profesional, or with status 400 (Bad Request) if the profesional has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profesionals")
    @Timed
    public ResponseEntity<Profesional> createProfesional(@RequestBody Profesional profesional) throws URISyntaxException {
        log.debug("REST request to save Profesional : {}", profesional);
        if (profesional.getId() != null) {
            throw new BadRequestAlertException("A new profesional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Profesional result = profesionalService.save(profesional);
        return ResponseEntity.created(new URI("/api/profesionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profesionals : Updates an existing profesional.
     *
     * @param profesional the profesional to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profesional,
     * or with status 400 (Bad Request) if the profesional is not valid,
     * or with status 500 (Internal Server Error) if the profesional couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/profesionals")
    @Timed
    public ResponseEntity<Profesional> updateProfesional(@RequestBody Profesional profesional) throws URISyntaxException {
        log.debug("REST request to update Profesional : {}", profesional);
        if (profesional.getId() == null) {
            return createProfesional(profesional);
        }
        Profesional result = profesionalService.save(profesional);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profesional.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profesionals : get all the profesionals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of profesionals in body
     */
    @GetMapping("/profesionals")
    @Timed
    public List<Profesional> getAllProfesionals() {
        log.debug("REST request to get all Profesionals");
        return profesionalService.findAll();
        }

    /**
     * GET  /profesionals/:id : get the "id" profesional.
     *
     * @param id the id of the profesional to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profesional, or with status 404 (Not Found)
     */
    @GetMapping("/profesionals/{id}")
    @Timed
    public ResponseEntity<Profesional> getProfesional(@PathVariable Long id) {
        log.debug("REST request to get Profesional : {}", id);
        Profesional profesional = profesionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(profesional));
    }

    /**
     * DELETE  /profesionals/:id : delete the "id" profesional.
     *
     * @param id the id of the profesional to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/profesionals/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfesional(@PathVariable Long id) {
        log.debug("REST request to delete Profesional : {}", id);
        profesionalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
