package municipalidad.obras.web.rest;

import com.codahale.metrics.annotation.Timed;
import municipalidad.obras.domain.ContactoProfesional;
import municipalidad.obras.service.ContactoProfesionalService;
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
 * REST controller for managing ContactoProfesional.
 */
@RestController
@RequestMapping("/api")
public class ContactoProfesionalResource {

    private final Logger log = LoggerFactory.getLogger(ContactoProfesionalResource.class);

    private static final String ENTITY_NAME = "contactoProfesional";

    private final ContactoProfesionalService contactoProfesionalService;

    public ContactoProfesionalResource(ContactoProfesionalService contactoProfesionalService) {
        this.contactoProfesionalService = contactoProfesionalService;
    }

    /**
     * POST  /contacto-profesionals : Create a new contactoProfesional.
     *
     * @param contactoProfesional the contactoProfesional to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactoProfesional, or with status 400 (Bad Request) if the contactoProfesional has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacto-profesionals")
    @Timed
    public ResponseEntity<ContactoProfesional> createContactoProfesional(@RequestBody ContactoProfesional contactoProfesional) throws URISyntaxException {
        log.debug("REST request to save ContactoProfesional : {}", contactoProfesional);
        if (contactoProfesional.getId() != null) {
            throw new BadRequestAlertException("A new contactoProfesional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactoProfesional result = contactoProfesionalService.save(contactoProfesional);
        return ResponseEntity.created(new URI("/api/contacto-profesionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contacto-profesionals : Updates an existing contactoProfesional.
     *
     * @param contactoProfesional the contactoProfesional to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactoProfesional,
     * or with status 400 (Bad Request) if the contactoProfesional is not valid,
     * or with status 500 (Internal Server Error) if the contactoProfesional couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contacto-profesionals")
    @Timed
    public ResponseEntity<ContactoProfesional> updateContactoProfesional(@RequestBody ContactoProfesional contactoProfesional) throws URISyntaxException {
        log.debug("REST request to update ContactoProfesional : {}", contactoProfesional);
        if (contactoProfesional.getId() == null) {
            return createContactoProfesional(contactoProfesional);
        }
        ContactoProfesional result = contactoProfesionalService.save(contactoProfesional);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactoProfesional.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contacto-profesionals : get all the contactoProfesionals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contactoProfesionals in body
     */
    @GetMapping("/contacto-profesionals")
    @Timed
    public List<ContactoProfesional> getAllContactoProfesionals() {
        log.debug("REST request to get all ContactoProfesionals");
        return contactoProfesionalService.findAll();
        }

    /**
     * GET  /contacto-profesionals/:id : get the "id" contactoProfesional.
     *
     * @param id the id of the contactoProfesional to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactoProfesional, or with status 404 (Not Found)
     */
    @GetMapping("/contacto-profesionals/{id}")
    @Timed
    public ResponseEntity<ContactoProfesional> getContactoProfesional(@PathVariable Long id) {
        log.debug("REST request to get ContactoProfesional : {}", id);
        ContactoProfesional contactoProfesional = contactoProfesionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contactoProfesional));
    }

    /**
     * DELETE  /contacto-profesionals/:id : delete the "id" contactoProfesional.
     *
     * @param id the id of the contactoProfesional to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contacto-profesionals/{id}")
    @Timed
    public ResponseEntity<Void> deleteContactoProfesional(@PathVariable Long id) {
        log.debug("REST request to delete ContactoProfesional : {}", id);
        contactoProfesionalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
