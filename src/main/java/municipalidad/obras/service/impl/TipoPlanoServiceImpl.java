package municipalidad.obras.service.impl;

import municipalidad.obras.service.TipoPlanoService;
import municipalidad.obras.domain.TipoPlano;
import municipalidad.obras.repository.TipoPlanoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing TipoPlano.
 */
@Service
@Transactional
public class TipoPlanoServiceImpl implements TipoPlanoService{

    private final Logger log = LoggerFactory.getLogger(TipoPlanoServiceImpl.class);

    private final TipoPlanoRepository tipoPlanoRepository;

    public TipoPlanoServiceImpl(TipoPlanoRepository tipoPlanoRepository) {
        this.tipoPlanoRepository = tipoPlanoRepository;
    }

    /**
     * Save a tipoPlano.
     *
     * @param tipoPlano the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoPlano save(TipoPlano tipoPlano) {
        log.debug("Request to save TipoPlano : {}", tipoPlano);
        return tipoPlanoRepository.save(tipoPlano);
    }

    /**
     * Get all the tipoPlanos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TipoPlano> findAll() {
        log.debug("Request to get all TipoPlanos");
        return tipoPlanoRepository.findAll();
    }


    /**
     *  get all the tipoPlanos where Oficina is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TipoPlano> findAllWhereOficinaIsNull() {
        log.debug("Request to get all tipoPlanos where Oficina is null");
        return StreamSupport
            .stream(tipoPlanoRepository.findAll().spliterator(), false)
            .filter(tipoPlano -> tipoPlano.getOficina() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one tipoPlano by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TipoPlano findOne(Long id) {
        log.debug("Request to get TipoPlano : {}", id);
        return tipoPlanoRepository.findOne(id);
    }

    /**
     * Delete the tipoPlano by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoPlano : {}", id);
        tipoPlanoRepository.delete(id);
    }
}
