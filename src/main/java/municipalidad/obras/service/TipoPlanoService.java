package municipalidad.obras.service;

import municipalidad.obras.domain.TipoPlano;
import java.util.List;

/**
 * Service Interface for managing TipoPlano.
 */
public interface TipoPlanoService {

    /**
     * Save a tipoPlano.
     *
     * @param tipoPlano the entity to save
     * @return the persisted entity
     */
    TipoPlano save(TipoPlano tipoPlano);

    /**
     * Get all the tipoPlanos.
     *
     * @return the list of entities
     */
    List<TipoPlano> findAll();
    /**
     * Get all the TipoPlanoDTO where Oficina is null.
     *
     * @return the list of entities
     */
    List<TipoPlano> findAllWhereOficinaIsNull();

    /**
     * Get the "id" tipoPlano.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TipoPlano findOne(Long id);

    /**
     * Delete the "id" tipoPlano.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
