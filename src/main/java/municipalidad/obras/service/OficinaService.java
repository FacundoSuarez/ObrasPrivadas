package municipalidad.obras.service;

import municipalidad.obras.domain.Oficina;
import java.util.List;

/**
 * Service Interface for managing Oficina.
 */
public interface OficinaService {

    /**
     * Save a oficina.
     *
     * @param oficina the entity to save
     * @return the persisted entity
     */
    Oficina save(Oficina oficina);

    /**
     * Get all the oficinas.
     *
     * @return the list of entities
     */
    List<Oficina> findAll();
    /**
     * Get all the OficinaDTO where Operador is null.
     *
     * @return the list of entities
     */
    List<Oficina> findAllWhereOperadorIsNull();

    /**
     * Get the "id" oficina.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Oficina findOne(Long id);

    /**
     * Delete the "id" oficina.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
