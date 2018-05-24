package municipalidad.obras.service.impl;

import municipalidad.obras.service.ProfesionalService;
import municipalidad.obras.domain.Profesional;
import municipalidad.obras.repository.ProfesionalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Profesional.
 */
@Service
@Transactional
public class ProfesionalServiceImpl implements ProfesionalService{

    private final Logger log = LoggerFactory.getLogger(ProfesionalServiceImpl.class);

    private final ProfesionalRepository profesionalRepository;

    public ProfesionalServiceImpl(ProfesionalRepository profesionalRepository) {
        this.profesionalRepository = profesionalRepository;
    }

    /**
     * Save a profesional.
     *
     * @param profesional the entity to save
     * @return the persisted entity
     */
    @Override
    public Profesional save(Profesional profesional) {
        log.debug("Request to save Profesional : {}", profesional);
        return profesionalRepository.save(profesional);
    }

    /**
     * Get all the profesionals.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Profesional> findAll() {
        log.debug("Request to get all Profesionals");
        return profesionalRepository.findAll();
    }

    /**
     * Get one profesional by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Profesional findOne(Long id) {
        log.debug("Request to get Profesional : {}", id);
        return profesionalRepository.findOne(id);
    }

    /**
     * Delete the profesional by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Profesional : {}", id);
        profesionalRepository.delete(id);
    }
}
