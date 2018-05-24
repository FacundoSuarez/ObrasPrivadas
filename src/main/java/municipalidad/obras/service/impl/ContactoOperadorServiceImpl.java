package municipalidad.obras.service.impl;

import municipalidad.obras.service.ContactoOperadorService;
import municipalidad.obras.domain.ContactoOperador;
import municipalidad.obras.repository.ContactoOperadorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing ContactoOperador.
 */
@Service
@Transactional
public class ContactoOperadorServiceImpl implements ContactoOperadorService{

    private final Logger log = LoggerFactory.getLogger(ContactoOperadorServiceImpl.class);

    private final ContactoOperadorRepository contactoOperadorRepository;

    public ContactoOperadorServiceImpl(ContactoOperadorRepository contactoOperadorRepository) {
        this.contactoOperadorRepository = contactoOperadorRepository;
    }

    /**
     * Save a contactoOperador.
     *
     * @param contactoOperador the entity to save
     * @return the persisted entity
     */
    @Override
    public ContactoOperador save(ContactoOperador contactoOperador) {
        log.debug("Request to save ContactoOperador : {}", contactoOperador);
        return contactoOperadorRepository.save(contactoOperador);
    }

    /**
     * Get all the contactoOperadors.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContactoOperador> findAll() {
        log.debug("Request to get all ContactoOperadors");
        return contactoOperadorRepository.findAll();
    }

    /**
     * Get one contactoOperador by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContactoOperador findOne(Long id) {
        log.debug("Request to get ContactoOperador : {}", id);
        return contactoOperadorRepository.findOne(id);
    }

    /**
     * Delete the contactoOperador by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactoOperador : {}", id);
        contactoOperadorRepository.delete(id);
    }
}
