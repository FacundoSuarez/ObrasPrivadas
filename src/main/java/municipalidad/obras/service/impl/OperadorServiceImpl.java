package municipalidad.obras.service.impl;

import municipalidad.obras.service.OperadorService;
import municipalidad.obras.domain.Operador;
import municipalidad.obras.repository.OperadorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Operador.
 */
@Service
@Transactional
public class OperadorServiceImpl implements OperadorService{

    private final Logger log = LoggerFactory.getLogger(OperadorServiceImpl.class);

    private final OperadorRepository operadorRepository;

    public OperadorServiceImpl(OperadorRepository operadorRepository) {
        this.operadorRepository = operadorRepository;
    }

    /**
     * Save a operador.
     *
     * @param operador the entity to save
     * @return the persisted entity
     */
    @Override
    public Operador save(Operador operador) {
        log.debug("Request to save Operador : {}", operador);
        return operadorRepository.save(operador);
    }

    /**
     * Get all the operadors.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Operador> findAll() {
        log.debug("Request to get all Operadors");
        return operadorRepository.findAll();
    }

    /**
     * Get one operador by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Operador findOne(Long id) {
        log.debug("Request to get Operador : {}", id);
        return operadorRepository.findOne(id);
    }

    /**
     * Delete the operador by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Operador : {}", id);
        operadorRepository.delete(id);
    }
}
