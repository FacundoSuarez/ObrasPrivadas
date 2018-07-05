package municipalidad.obras.service;

import java.util.List;

import municipalidad.dto.PlanoDTO;
import municipalidad.obras.domain.Plano;

/**
 * Service Interface for managing Plano.
 */
public interface PlanoService {

    /**
     * Save a plano.
     *
     * @param plano the entity to save
     * @return the persisted entity
     */
    Plano save(Plano plano);

    /**
     * Get all the planos.
     *
     * @return the list of entities
     */
    List<Plano> findAll();

    /**
     * Get the "id" plano.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Plano findOne(Long id);

    /**
     * Delete the "id" plano.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    ///////////////////////////////////////////////////
    ///////////////////////////////////////////////////
    
    Plano saveDTO(PlanoDTO dto);
    PlanoDTO findOneDTO(Long id);
    
    
    
  
}
