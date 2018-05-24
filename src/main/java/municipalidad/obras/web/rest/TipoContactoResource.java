package municipalidad.obras.web.rest;

import com.codahale.metrics.annotation.Timed;
import municipalidad.obras.domain.TipoContacto;
import municipalidad.obras.service.TipoContactoService;
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
 * REST controller for managing TipoContacto.
 */
@RestController
@RequestMapping("/api")
public class TipoContactoResource {

    private final Logger log = LoggerFactory.getLogger(TipoContactoResource.class);

    private static final String ENTITY_NAME = "tipoContacto";

    private final TipoContactoService tipoContactoService;

    public TipoContactoResource(TipoContactoService tipoContactoService) {
        this.tipoContactoService = tipoContactoService;
    }

    /**
     * POST  /tipo-contactos : Create a new tipoContacto.
     *
     * @param tipoContacto the tipoContacto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoContacto, or with status 400 (Bad Request) if the tipoContacto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-contactos")
    @Timed
    public ResponseEntity<TipoContacto> createTipoContacto(@RequestBody TipoContacto tipoContacto) throws URISyntaxException {
        log.debug("REST request to save TipoContacto : {}", tipoContacto);
        if (tipoContacto.getId() != null) {
            throw new BadRequestAlertException("A new tipoContacto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoContacto result = tipoContactoService.save(tipoContacto);
        return ResponseEntity.created(new URI("/api/tipo-contactos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-contactos : Updates an existing tipoContacto.
     *
     * @param tipoContacto the tipoContacto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoContacto,
     * or with status 400 (Bad Request) if the tipoContacto is not valid,
     * or with status 500 (Internal Server Error) if the tipoContacto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-contactos")
    @Timed
    public ResponseEntity<TipoContacto> updateTipoContacto(@RequestBody TipoContacto tipoContacto) throws URISyntaxException {
        log.debug("REST request to update TipoContacto : {}", tipoContacto);
        if (tipoContacto.getId() == null) {
            return createTipoContacto(tipoContacto);
        }
        TipoContacto result = tipoContactoService.save(tipoContacto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoContacto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-contactos : get all the tipoContactos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoContactos in body
     */
    @GetMapping("/tipo-contactos")
    @Timed
    public List<TipoContacto> getAllTipoContactos() {
        log.debug("REST request to get all TipoContactos");
        return tipoContactoService.findAll();
        }

    /**
     * GET  /tipo-contactos/:id : get the "id" tipoContacto.
     *
     * @param id the id of the tipoContacto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoContacto, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-contactos/{id}")
    @Timed
    public ResponseEntity<TipoContacto> getTipoContacto(@PathVariable Long id) {
        log.debug("REST request to get TipoContacto : {}", id);
        TipoContacto tipoContacto = tipoContactoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipoContacto));
    }

    /**
     * DELETE  /tipo-contactos/:id : delete the "id" tipoContacto.
     *
     * @param id the id of the tipoContacto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-contactos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoContacto(@PathVariable Long id) {
        log.debug("REST request to delete TipoContacto : {}", id);
        tipoContactoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
