package municipalidad.obras.service.impl;

import municipalidad.obras.service.OficinaService;
import municipalidad.obras.domain.Oficina;
import municipalidad.obras.repository.OficinaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Oficina.
 */
@Service
@Transactional
public class OficinaServiceImpl implements OficinaService{

    private final Logger log = LoggerFactory.getLogger(OficinaServiceImpl.class);

    private final OficinaRepository oficinaRepository;

    public OficinaServiceImpl(OficinaRepository oficinaRepository) {
        this.oficinaRepository = oficinaRepository;
    }

    /**
     * Save a oficina.
     *
     * @param oficina the entity to save
     * @return the persisted entity
     */
    @Override
    public Oficina save(Oficina oficina) {
        log.debug("Request to save Oficina : {}", oficina);
        return oficinaRepository.save(oficina);
    }

    /**
     * Get all the oficinas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Oficina> findAll() {
        log.debug("Request to get all Oficinas");
        return oficinaRepository.findAll();
    }


    /**
     *  get all the oficinas where Operador is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Oficina> findAllWhereOperadorIsNull() {
        log.debug("Request to get all oficinas where Operador is null");
        return StreamSupport
            .stream(oficinaRepository.findAll().spliterator(), false)
            .filter(oficina -> oficina.getOperador() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one oficina by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Oficina findOne(Long id) {
        log.debug("Request to get Oficina : {}", id);
        return oficinaRepository.findOne(id);
    }

    /**
     * Delete the oficina by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Oficina : {}", id);
        oficinaRepository.delete(id);
    }
}
