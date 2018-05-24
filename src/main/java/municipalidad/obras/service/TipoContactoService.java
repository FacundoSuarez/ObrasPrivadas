package municipalidad.obras.service;

import municipalidad.obras.domain.TipoContacto;
import java.util.List;

/**
 * Service Interface for managing TipoContacto.
 */
public interface TipoContactoService {

    /**
     * Save a tipoContacto.
     *
     * @param tipoContacto the entity to save
     * @return the persisted entity
     */
    TipoContacto save(TipoContacto tipoContacto);

    /**
     * Get all the tipoContactos.
     *
     * @return the list of entities
     */
    List<TipoContacto> findAll();

    /**
     * Get the "id" tipoContacto.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TipoContacto findOne(Long id);

    /**
     * Delete the "id" tipoContacto.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
