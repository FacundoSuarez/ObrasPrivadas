package municipalidad.obras.service.impl;

import java.util.ArrayList;
import municipalidad.obras.service.TramiteService;
import municipalidad.obras.domain.Tramite;
import municipalidad.obras.repository.TramiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import municipalidad.obras.service.dto.TramiteDTO;
import municipalidad.obras.domain.User;
import municipalidad.obras.repository.ArchivoRepository;
import municipalidad.obras.service.UserService;

/**
 * Service Implementation for managing Tramite.
 */
@Service
@Transactional
public class TramiteServiceImpl implements TramiteService{

    private final Logger log = LoggerFactory.getLogger(TramiteServiceImpl.class);

    private final TramiteRepository tramiteRepository;
    
    private final ArchivoRepository archivoRepository;
    
    private final UserService userService;

    public TramiteServiceImpl(TramiteRepository tramiteRepository, ArchivoRepository archivoRepository, UserService userService) {
        this.tramiteRepository = tramiteRepository;
        this.archivoRepository = archivoRepository;
        this.userService = userService;
    }
    
    /**
     * Save a tramite.
     *
     * @param tramite the entity to save
     * @return the persisted entity
     */
    @Override
    public Tramite save(Tramite tramite) {
        log.debug("Request to save Tramite : {}", tramite);
        return tramiteRepository.save(tramite);
    }
    
    @Override
    public Tramite firstSave(Tramite tramite){
        log.debug("Request to save Tramite : {}", tramite);
        return tramiteRepository.save(tramite);
    }
    

    /**
     * Get one tramite by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Tramite findOne(Long id) {
        log.debug("Request to get Tramite : {}", id);
        return tramiteRepository.findOne(id);
    }

    /**
     * Delete the tramite by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tramite : {}", id);
        tramiteRepository.delete(id);
    }
    
    
    /////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
    
    /**
     * Get all the tramites.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TramiteDTO> findAll() {
        log.debug("Request to get all Tramites");
        List<Tramite> tramites = tramiteRepository.findAll();
        List<TramiteDTO> retorno = new ArrayList<>();
        TramiteDTO dto = new TramiteDTO();
        Optional<User> user = userService.getUserWithAuthorities();
        for (Tramite tramite : tramites) {

            dto.setArchivos(archivoRepository.findByTramite(tramite));
            dto.setFecha(tramite.getFecha());
            dto.setFechaFin(tramite.getFechaFin());
            dto.setId(tramite.getId());
            dto.setObservaciones(tramite.getObservaciones());
            dto.setOperador(tramite.getOperador());
            dto.setPlanoDetalle(tramite.getPlanoDetalle());
            String login = user.get().getLogin();
            
            //MUESTRA SOLO LOS PLANOS DE QUIEN ESTA LOGGEADO
            if(login.equals(tramite.getPlanoDetalle().getPlano().getProfesional().getUsuario().getLogin()))    {
            retorno.add(dto);
            dto = new TramiteDTO();
            }
          
        }

        return retorno;
    }
}
