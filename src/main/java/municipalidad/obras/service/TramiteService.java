package municipalidad.obras.service;

import municipalidad.obras.domain.Tramite;
import java.util.List;
import municipalidad.dto.TramiteDTO;

/**
 * Service Interface for managing Tramite.
 */
public interface TramiteService {

    /**
     * Save a tramite.
     *
     * @param tramite the entity to save
     * @return the persisted entity
     */
    Tramite save(Tramite tramite);

    /**
     * Get all the tramites.
     *
     * @return the list of entities
     */
    //List<Tramite> findAll();

    /**
     * Get the "id" tramite.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Tramite findOne(Long id);

    /**
     * Delete the "id" tramite.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    ///////////////////////////////////////////////
    //////////////////////////////////////////////
    
    List<TramiteDTO> findAll();
}
