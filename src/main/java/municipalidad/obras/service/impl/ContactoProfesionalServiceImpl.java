package municipalidad.obras.service.impl;

import municipalidad.obras.service.ContactoProfesionalService;
import municipalidad.obras.domain.ContactoProfesional;
import municipalidad.obras.repository.ContactoProfesionalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing ContactoProfesional.
 */
@Service
@Transactional
public class ContactoProfesionalServiceImpl implements ContactoProfesionalService{

    private final Logger log = LoggerFactory.getLogger(ContactoProfesionalServiceImpl.class);

    private final ContactoProfesionalRepository contactoProfesionalRepository;

    public ContactoProfesionalServiceImpl(ContactoProfesionalRepository contactoProfesionalRepository) {
        this.contactoProfesionalRepository = contactoProfesionalRepository;
    }

    /**
     * Save a contactoProfesional.
     *
     * @param contactoProfesional the entity to save
     * @return the persisted entity
     */
    @Override
    public ContactoProfesional save(ContactoProfesional contactoProfesional) {
        log.debug("Request to save ContactoProfesional : {}", contactoProfesional);
        return contactoProfesionalRepository.save(contactoProfesional);
    }

    /**
     * Get all the contactoProfesionals.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContactoProfesional> findAll() {
        log.debug("Request to get all ContactoProfesionals");
        return contactoProfesionalRepository.findAll();
    }

    /**
     * Get one contactoProfesional by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContactoProfesional findOne(Long id) {
        log.debug("Request to get ContactoProfesional : {}", id);
        return contactoProfesionalRepository.findOne(id);
    }

    /**
     * Delete the contactoProfesional by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactoProfesional : {}", id);
        contactoProfesionalRepository.delete(id);
    }
}
