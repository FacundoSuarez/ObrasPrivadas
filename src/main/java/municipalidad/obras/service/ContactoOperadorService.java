package municipalidad.obras.service;

import municipalidad.obras.domain.ContactoOperador;
import java.util.List;

/**
 * Service Interface for managing ContactoOperador.
 */
public interface ContactoOperadorService {

    /**
     * Save a contactoOperador.
     *
     * @param contactoOperador the entity to save
     * @return the persisted entity
     */
    ContactoOperador save(ContactoOperador contactoOperador);

    /**
     * Get all the contactoOperadors.
     *
     * @return the list of entities
     */
    List<ContactoOperador> findAll();

    /**
     * Get the "id" contactoOperador.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ContactoOperador findOne(Long id);

    /**
     * Delete the "id" contactoOperador.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
