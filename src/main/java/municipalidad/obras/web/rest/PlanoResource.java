package municipalidad.obras.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import municipalidad.dto.PlanoDTO;
import municipalidad.obras.domain.Plano;
import municipalidad.obras.service.PlanoService;
import municipalidad.obras.web.rest.errors.BadRequestAlertException;
import municipalidad.obras.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Plano.
 */
@RestController
@RequestMapping("/api")
public class PlanoResource {

    private final Logger log = LoggerFactory.getLogger(PlanoResource.class);

    private static final String ENTITY_NAME = "plano";

    private final PlanoService planoService;

    public PlanoResource(PlanoService planoService) {
        this.planoService = planoService;
    }

    /**
     * POST  /planos : Create a new plano.
     *
     * @param plano the plano to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plano, or with status 400 (Bad Request) if the plano has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
//    @PostMapping("/planos")
//    @Timed
//    public ResponseEntity<Plano> createPlano(@RequestBody Plano plano) throws URISyntaxException {
//        log.debug("REST request to save Plano : {}", plano);
//        if (plano.getId() != null) {
//            throw new BadRequestAlertException("A new plano cannot already have an ID", ENTITY_NAME, "idexists");
//        }
//        Plano result = planoService.save(plano);
//        return ResponseEntity.created(new URI("/api/planos/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }

    /**
     * PUT  /planos : Updates an existing plano.
     *
     * @param plano the plano to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plano,
     * or with status 400 (Bad Request) if the plano is not valid,
     * or with status 500 (Internal Server Error) if the plano couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/planos")
    @Timed
    public ResponseEntity<Plano> updatePlano(@RequestBody Plano plano) throws URISyntaxException {
        log.debug("REST request to update Plano : {}", plano);
        if (plano.getId() == null) {
            //return createPlano(plano);
        }
        Plano result = planoService.save(plano);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plano.getId().toString()))
            .body(result);
    }

    /**
     * GET  /planos : get all the planos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of planos in body
     */
    @GetMapping("/planos")
    @Timed
    public List<Plano> getAllPlanos() {
        log.debug("REST request to get all Planos");
        return planoService.findAll();
        }

    /**
     * GET  /planos/:id : get the "id" plano.
     *
     * @param id the id of the plano to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plano, or with status 404 (Not Found)
     */
    @GetMapping("/planos/{id}")
    @Timed
    public PlanoDTO getPlano(@PathVariable Long id) {
        log.debug("REST request to get Plano : {}", id);
        PlanoDTO plano = planoService.findOneDTO(id);
        return plano;
    }

    /**
     * DELETE  /planos/:id : delete the "id" plano.
     *
     * @param id the id of the plano to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/planos/{id}")
    @Timed
    public ResponseEntity<Void> deletePlano(@PathVariable Long id) {
        log.debug("REST request to delete Plano : {}", id);
        planoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    @PostMapping("/planos")
    @Timed
    public ResponseEntity<Plano> createPlanoDTO(@RequestBody PlanoDTO plano) throws URISyntaxException {
        log.debug("REST request to save PlanoDTO : {}", plano);
        if (plano.getId() != null) {
            throw new BadRequestAlertException("A new plano cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        Plano result = planoService.saveDTO(plano);
        return ResponseEntity.created(new URI("/api/planos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    
    
    /**
     * GET  /planosDTO : get all the planos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of planos in body
     */
    @GetMapping("/planos-dto")
    @Timed
    public List<Plano> getAllPlanosDTO() {
        log.debug("REST request to get all Planos");
        return planoService.findAll();
        }
    
    
}
