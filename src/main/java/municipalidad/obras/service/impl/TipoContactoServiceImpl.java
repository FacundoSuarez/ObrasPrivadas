package municipalidad.obras.service.impl;

import municipalidad.obras.service.TipoContactoService;
import municipalidad.obras.domain.TipoContacto;
import municipalidad.obras.repository.TipoContactoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing TipoContacto.
 */
@Service
@Transactional
public class TipoContactoServiceImpl implements TipoContactoService{

    private final Logger log = LoggerFactory.getLogger(TipoContactoServiceImpl.class);

    private final TipoContactoRepository tipoContactoRepository;

    public TipoContactoServiceImpl(TipoContactoRepository tipoContactoRepository) {
        this.tipoContactoRepository = tipoContactoRepository;
    }

    /**
     * Save a tipoContacto.
     *
     * @param tipoContacto the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoContacto save(TipoContacto tipoContacto) {
        log.debug("Request to save TipoContacto : {}", tipoContacto);
        return tipoContactoRepository.save(tipoContacto);
    }

    /**
     * Get all the tipoContactos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TipoContacto> findAll() {
        log.debug("Request to get all TipoContactos");
        return tipoContactoRepository.findAll();
    }

    /**
     * Get one tipoContacto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TipoContacto findOne(Long id) {
        log.debug("Request to get TipoContacto : {}", id);
        return tipoContactoRepository.findOne(id);
    }

    /**
     * Delete the tipoContacto by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoContacto : {}", id);
        tipoContactoRepository.delete(id);
    }
}
