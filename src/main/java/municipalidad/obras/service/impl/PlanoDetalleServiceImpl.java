package municipalidad.obras.service.impl;

import municipalidad.obras.service.PlanoDetalleService;
import municipalidad.obras.domain.PlanoDetalle;
import municipalidad.obras.repository.PlanoDetalleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PlanoDetalle.
 */
@Service
@Transactional
public class PlanoDetalleServiceImpl implements PlanoDetalleService{

    private final Logger log = LoggerFactory.getLogger(PlanoDetalleServiceImpl.class);

    private final PlanoDetalleRepository planoDetalleRepository;

    public PlanoDetalleServiceImpl(PlanoDetalleRepository planoDetalleRepository) {
        this.planoDetalleRepository = planoDetalleRepository;
    }

    /**
     * Save a planoDetalle.
     *
     * @param planoDetalle the entity to save
     * @return the persisted entity
     */
    @Override
    public PlanoDetalle save(PlanoDetalle planoDetalle) {
        log.debug("Request to save PlanoDetalle : {}", planoDetalle);
        return planoDetalleRepository.save(planoDetalle);
    }

    /**
     * Get all the planoDetalles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PlanoDetalle> findAll(Pageable pageable) {
        log.debug("Request to get all PlanoDetalles");
        return planoDetalleRepository.findAll(pageable);
    }

    /**
     * Get one planoDetalle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PlanoDetalle findOne(Long id) {
        log.debug("Request to get PlanoDetalle : {}", id);
        return planoDetalleRepository.findOne(id);
    }

    /**
     * Delete the planoDetalle by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanoDetalle : {}", id);
        planoDetalleRepository.delete(id);
    }
}
