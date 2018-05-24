package municipalidad.obras.service.impl;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import municipalidad.dto.ArchivoDTO;
import municipalidad.dto.PlanoDetalleDTO;
import municipalidad.dto.TramiteDTO;
import municipalidad.obras.service.ArchivoService;
import municipalidad.obras.domain.Archivo;
import municipalidad.obras.domain.PlanoDetalle;
import municipalidad.obras.domain.Tramite;
import municipalidad.obras.domain.User;
import municipalidad.obras.repository.ArchivoRepository;
import municipalidad.obras.repository.OperadorRepository;
import municipalidad.obras.service.MailService;
import municipalidad.obras.service.PlanoDetalleService;
import municipalidad.obras.service.TramiteService;
import municipalidad.obras.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Archivo.
 */
@Service
@Transactional
public class ArchivoServiceImpl implements ArchivoService{

    private final Logger log = LoggerFactory.getLogger(ArchivoServiceImpl.class);

    private final ArchivoRepository archivoRepository;
    
    private final PlanoDetalleService planoDetalleService;
    
    private final UserService userService;
    
    private final MailService mailService;
    
    private final OperadorRepository operadorRepository;
    
    private final TramiteService tramiteService;
    
    
//    private final ArchivoService archivoService; //    private final ArchivoService archivoService; 

    public ArchivoServiceImpl(ArchivoRepository archivoRepository, PlanoDetalleService planoDetalleService, UserService userService, MailService mailService, OperadorRepository operadorRepository, TramiteService tramiteService) {
        this.archivoRepository = archivoRepository;
        this.planoDetalleService = planoDetalleService;
        this.userService = userService;
        this.mailService = mailService;
        this.operadorRepository = operadorRepository;
        this.tramiteService = tramiteService;
    }

    /**
     * Save a archivo.
     *
     * @param archivo the entity to save
     * @return the persisted entity
     */
    @Override
    public Archivo save(Archivo archivo) {
        log.debug("Request to save Archivo : {}", archivo);
        //Envía email notificaciones
        User email = archivo.getTramite().getPlanoDetalle().getPlano().getProfesional().getUsuario();
        mailService.sendNotificationMail(email);

        //Setea los campos que faltan en todo el tramite
        Instant now = Instant.now();
        ZoneId zoneId = ZoneId.of("UTC-3");
        Optional<User> user = userService.getUserWithAuthorities();
        archivo.getTramite().setOperador(operadorRepository.findByUsuario(user.get()));
        archivo.getTramite().setFechaFin(ZonedDateTime.ofInstant(now, zoneId));

        //Guarda PlanoDetalle
        planoDetalleService.save(archivo.getTramite().getPlanoDetalle());

        //Guarda Tramite
        tramiteService.save(archivo.getTramite());

        return archivoRepository.save(archivo);
    }
    
    
    
    //@Override
    public Archivo firstSave(Archivo archivo){
        log.debug("Request to save Archivo : {}", archivo);
        return archivoRepository.save(archivo);
    }

    /**
     * Get all the archivos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Archivo> findAll(Pageable pageable) {
        log.debug("Request to get all Archivos");
        return archivoRepository.findAll(pageable);
    }

    /**
     * Get one archivo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Archivo findOne(Long id) {
        log.debug("Request to get Archivo : {}", id);
        return archivoRepository.findOne(id);
    }

    /**
     * Delete the archivo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Archivo : {}", id);
        archivoRepository.delete(id);
    }
    
    
    //@Override
//    public Archivo saveDTO(ArchivoDTO dto) {
//        Archivo archivo = new Archivo();
//        Archivo result = archivoRepository.save(archivo);
//        result.setArchivo(dto.getArchivo());
//        result.setId(dto.getId());
//        
//        for (PlanoDetalleDTO detalle : dto.getPlanoDetalles()) {
//        	
//        	EstadoPlano estadoPlano = null;
//        	detalle.setEstado(estadoPlano.REVISION);                
//        	
//            PlanoDetalle planoDetalle = planoDetalleService.save(getConvertPlanoDetalleFromDTO(detalle));
//        
//        }
//        archivoRepository.save(archivo);
//        return result;
//    }
      
}
