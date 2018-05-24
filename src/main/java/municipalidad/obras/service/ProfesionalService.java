package municipalidad.obras.service;

import municipalidad.obras.domain.Profesional;
import java.util.List;

/**
 * Service Interface for managing Profesional.
 */
public interface ProfesionalService {

    /**
     * Save a profesional.
     *
     * @param profesional the entity to save
     * @return the persisted entity
     */
    Profesional save(Profesional profesional);

    /**
     * Get all the profesionals.
     *
     * @return the list of entities
     */
    List<Profesional> findAll();

    /**
     * Get the "id" profesional.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Profesional findOne(Long id);

    /**
     * Delete the "id" profesional.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
