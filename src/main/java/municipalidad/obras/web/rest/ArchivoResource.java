package municipalidad.obras.web.rest;

import com.codahale.metrics.annotation.Timed;
import municipalidad.obras.domain.Archivo;
import municipalidad.obras.service.ArchivoService;
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
import municipalidad.dto.ArchivoDTO;

/**
 * REST controller for managing Archivo.
 */
@RestController
@RequestMapping("/api")
public class ArchivoResource {

    private final Logger log = LoggerFactory.getLogger(ArchivoResource.class);

    private static final String ENTITY_NAME = "archivo";

    private final ArchivoService archivoService;

    public ArchivoResource(ArchivoService archivoService) {
        this.archivoService = archivoService;
    }

    /**
     * POST  /archivos : Create a new archivo.
     *
     * @param archivo the archivo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new archivo, or with status 400 (Bad Request) if the archivo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/archivos")
    @Timed
    public ResponseEntity<Archivo> createArchivo(@RequestBody Archivo archivo) throws URISyntaxException {
        log.debug("REST request to save Archivo : {}", archivo);
        if (archivo.getId() != null) {
            throw new BadRequestAlertException("A new archivo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Archivo result = archivoService.save(archivo);
        return ResponseEntity.created(new URI("/api/archivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /archivos : Updates an existing archivo.
     *
     * @param archivo the archivo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated archivo,
     * or with status 400 (Bad Request) if the archivo is not valid,
     * or with status 500 (Internal Server Error) if the archivo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/archivos")
    @Timed
    public ResponseEntity<Archivo> updateArchivo(@RequestBody Archivo archivo) throws URISyntaxException {
        log.debug("REST request to update Archivo : {}", archivo);
        if (archivo.getId() == null) {
            return createArchivo(archivo);
        }
        Archivo result = archivoService.save(archivo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, archivo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /archivos : get all the archivos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of archivos in body
     */
    @GetMapping("/archivos")
    @Timed
    public ResponseEntity<List<Archivo>> getAllArchivos(Pageable pageable) {
        log.debug("REST request to get a page of Archivos");
        Page<Archivo> page = archivoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/archivos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /archivos/:id : get the "id" archivo.
     *
     * @param id the id of the archivo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the archivo, or with status 404 (Not Found)
     */
    @GetMapping("/archivos/{id}")
    @Timed
    public ResponseEntity<Archivo> getArchivo(@PathVariable Long id) {
        log.debug("REST request to get Archivo : {}", id);
        Archivo archivo = archivoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(archivo));
    }

    /**
     * DELETE  /archivos/:id : delete the "id" archivo.
     *
     * @param id the id of the archivo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/archivos/{id}")
    @Timed
    public ResponseEntity<Void> deleteArchivo(@PathVariable Long id) {
        log.debug("REST request to delete Archivo : {}", id);
        archivoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
//    @PutMapping("/archivos")
//    @Timed
//    public ResponseEntity<Archivo> updateArchivoDTO(@RequestBody ArchivoDTO archivo) throws URISyntaxException {
//        log.debug("REST request to update Archivo : {}", archivo);
//        if (archivo.getId() == null) {
//            //return createArchivo(archivo);
//        }
//        Archivo result = archivoService.saveDTO(archivo);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, archivo.getId().toString()))
//            .body(result);
//    }
}
