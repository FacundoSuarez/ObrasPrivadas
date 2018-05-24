package municipalidad.obras.service;

import municipalidad.obras.domain.PlanoDetalle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PlanoDetalle.
 */
public interface PlanoDetalleService {

    /**
     * Save a planoDetalle.
     *
     * @param planoDetalle the entity to save
     * @return the persisted entity
     */
    PlanoDetalle save(PlanoDetalle planoDetalle);

    /**
     * Get all the planoDetalles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PlanoDetalle> findAll(Pageable pageable);

    /**
     * Get the "id" planoDetalle.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PlanoDetalle findOne(Long id);

    /**
     * Delete the "id" planoDetalle.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
