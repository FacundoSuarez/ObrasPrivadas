package municipalidad.obras.service;

import municipalidad.dto.ArchivoDTO;
import municipalidad.obras.domain.Archivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Archivo.
 */
public interface ArchivoService {

    /**
     * Save a archivo.
     *
     * @param archivo the entity to save
     * @return the persisted entity
     */
    Archivo save(Archivo archivo);

    /**
     * Get all the archivos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Archivo> findAll(Pageable pageable);

    /**
     * Get the "id" archivo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Archivo findOne(Long id);

    /**
     * Delete the "id" archivo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    
    Archivo firstSave(Archivo archivo);
}
