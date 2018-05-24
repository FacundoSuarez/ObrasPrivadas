package municipalidad.obras.service;

import municipalidad.obras.domain.ContactoProfesional;
import java.util.List;

/**
 * Service Interface for managing ContactoProfesional.
 */
public interface ContactoProfesionalService {

    /**
     * Save a contactoProfesional.
     *
     * @param contactoProfesional the entity to save
     * @return the persisted entity
     */
    ContactoProfesional save(ContactoProfesional contactoProfesional);

    /**
     * Get all the contactoProfesionals.
     *
     * @return the list of entities
     */
    List<ContactoProfesional> findAll();

    /**
     * Get the "id" contactoProfesional.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ContactoProfesional findOne(Long id);

    /**
     * Delete the "id" contactoProfesional.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
