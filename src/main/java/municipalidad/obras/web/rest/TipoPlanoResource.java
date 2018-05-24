package municipalidad.obras.web.rest;

import com.codahale.metrics.annotation.Timed;
import municipalidad.obras.domain.TipoPlano;
import municipalidad.obras.service.TipoPlanoService;
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
 * REST controller for managing TipoPlano.
 */
@RestController
@RequestMapping("/api")
public class TipoPlanoResource {

    private final Logger log = LoggerFactory.getLogger(TipoPlanoResource.class);

    private static final String ENTITY_NAME = "tipoPlano";

    private final TipoPlanoService tipoPlanoService;

    public TipoPlanoResource(TipoPlanoService tipoPlanoService) {
        this.tipoPlanoService = tipoPlanoService;
    }

    /**
     * POST  /tipo-planos : Create a new tipoPlano.
     *
     * @param tipoPlano the tipoPlano to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoPlano, or with status 400 (Bad Request) if the tipoPlano has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-planos")
    @Timed
    public ResponseEntity<TipoPlano> createTipoPlano(@RequestBody TipoPlano tipoPlano) throws URISyntaxException {
        log.debug("REST request to save TipoPlano : {}", tipoPlano);
        if (tipoPlano.getId() != null) {
            throw new BadRequestAlertException("A new tipoPlano cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoPlano result = tipoPlanoService.save(tipoPlano);
        return ResponseEntity.created(new URI("/api/tipo-planos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-planos : Updates an existing tipoPlano.
     *
     * @param tipoPlano the tipoPlano to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoPlano,
     * or with status 400 (Bad Request) if the tipoPlano is not valid,
     * or with status 500 (Internal Server Error) if the tipoPlano couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-planos")
    @Timed
    public ResponseEntity<TipoPlano> updateTipoPlano(@RequestBody TipoPlano tipoPlano) throws URISyntaxException {
        log.debug("REST request to update TipoPlano : {}", tipoPlano);
        if (tipoPlano.getId() == null) {
            return createTipoPlano(tipoPlano);
        }
        TipoPlano result = tipoPlanoService.save(tipoPlano);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoPlano.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-planos : get all the tipoPlanos.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of tipoPlanos in body
     */
    @GetMapping("/tipo-planos")
    @Timed
    public List<TipoPlano> getAllTipoPlanos(@RequestParam(required = false) String filter) {
        if ("oficina-is-null".equals(filter)) {
            log.debug("REST request to get all TipoPlanos where oficina is null");
            return tipoPlanoService.findAllWhereOficinaIsNull();
        }
        log.debug("REST request to get all TipoPlanos");
        return tipoPlanoService.findAll();
        }

    /**
     * GET  /tipo-planos/:id : get the "id" tipoPlano.
     *
     * @param id the id of the tipoPlano to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoPlano, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-planos/{id}")
    @Timed
    public ResponseEntity<TipoPlano> getTipoPlano(@PathVariable Long id) {
        log.debug("REST request to get TipoPlano : {}", id);
        TipoPlano tipoPlano = tipoPlanoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipoPlano));
    }

    /**
     * DELETE  /tipo-planos/:id : delete the "id" tipoPlano.
     *
     * @param id the id of the tipoPlano to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-planos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoPlano(@PathVariable Long id) {
        log.debug("REST request to delete TipoPlano : {}", id);
        tipoPlanoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
