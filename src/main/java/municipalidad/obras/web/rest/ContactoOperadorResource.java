package municipalidad.obras.web.rest;

import com.codahale.metrics.annotation.Timed;
import municipalidad.obras.domain.ContactoOperador;
import municipalidad.obras.service.ContactoOperadorService;
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
 * REST controller for managing ContactoOperador.
 */
@RestController
@RequestMapping("/api")
public class ContactoOperadorResource {

    private final Logger log = LoggerFactory.getLogger(ContactoOperadorResource.class);

    private static final String ENTITY_NAME = "contactoOperador";

    private final ContactoOperadorService contactoOperadorService;

    public ContactoOperadorResource(ContactoOperadorService contactoOperadorService) {
        this.contactoOperadorService = contactoOperadorService;
    }

    /**
     * POST  /contacto-operadors : Create a new contactoOperador.
     *
     * @param contactoOperador the contactoOperador to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactoOperador, or with status 400 (Bad Request) if the contactoOperador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacto-operadors")
    @Timed
    public ResponseEntity<ContactoOperador> createContactoOperador(@RequestBody ContactoOperador contactoOperador) throws URISyntaxException {
        log.debug("REST request to save ContactoOperador : {}", contactoOperador);
        if (contactoOperador.getId() != null) {
            throw new BadRequestAlertException("A new contactoOperador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactoOperador result = contactoOperadorService.save(contactoOperador);
        return ResponseEntity.created(new URI("/api/contacto-operadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contacto-operadors : Updates an existing contactoOperador.
     *
     * @param contactoOperador the contactoOperador to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactoOperador,
     * or with status 400 (Bad Request) if the contactoOperador is not valid,
     * or with status 500 (Internal Server Error) if the contactoOperador couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contacto-operadors")
    @Timed
    public ResponseEntity<ContactoOperador> updateContactoOperador(@RequestBody ContactoOperador contactoOperador) throws URISyntaxException {
        log.debug("REST request to update ContactoOperador : {}", contactoOperador);
        if (contactoOperador.getId() == null) {
            return createContactoOperador(contactoOperador);
        }
        ContactoOperador result = contactoOperadorService.save(contactoOperador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactoOperador.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contacto-operadors : get all the contactoOperadors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contactoOperadors in body
     */
    @GetMapping("/contacto-operadors")
    @Timed
    public List<ContactoOperador> getAllContactoOperadors() {
        log.debug("REST request to get all ContactoOperadors");
        return contactoOperadorService.findAll();
        }

    /**
     * GET  /contacto-operadors/:id : get the "id" contactoOperador.
     *
     * @param id the id of the contactoOperador to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactoOperador, or with status 404 (Not Found)
     */
    @GetMapping("/contacto-operadors/{id}")
    @Timed
    public ResponseEntity<ContactoOperador> getContactoOperador(@PathVariable Long id) {
        log.debug("REST request to get ContactoOperador : {}", id);
        ContactoOperador contactoOperador = contactoOperadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contactoOperador));
    }

    /**
     * DELETE  /contacto-operadors/:id : delete the "id" contactoOperador.
     *
     * @param id the id of the contactoOperador to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contacto-operadors/{id}")
    @Timed
    public ResponseEntity<Void> deleteContactoOperador(@PathVariable Long id) {
        log.debug("REST request to delete ContactoOperador : {}", id);
        contactoOperadorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
