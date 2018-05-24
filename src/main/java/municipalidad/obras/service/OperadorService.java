package municipalidad.obras.service;

import municipalidad.obras.domain.Operador;
import java.util.List;

/**
 * Service Interface for managing Operador.
 */
public interface OperadorService {

    /**
     * Save a operador.
     *
     * @param operador the entity to save
     * @return the persisted entity
     */
    Operador save(Operador operador);

    /**
     * Get all the operadors.
     *
     * @return the list of entities
     */
    List<Operador> findAll();

    /**
     * Get the "id" operador.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Operador findOne(Long id);

    /**
     * Delete the "id" operador.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
