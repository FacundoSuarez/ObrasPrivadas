    package municipalidad.obras.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import municipalidad.dto.PlanoDTO;
import municipalidad.dto.PlanoDetalleDTO;
import municipalidad.obras.domain.Archivo;
import municipalidad.obras.domain.Plano;
import municipalidad.obras.domain.PlanoDetalle;
import municipalidad.obras.domain.Tramite;
import municipalidad.obras.domain.User;
import municipalidad.obras.domain.enumeration.EstadoPlano;
import municipalidad.obras.repository.PlanoRepository;
import municipalidad.obras.repository.ProfesionalRepository;
import municipalidad.obras.service.ArchivoService;
import municipalidad.obras.service.PlanoDetalleService;
import municipalidad.obras.service.PlanoService;
import municipalidad.obras.service.TramiteService;
import municipalidad.obras.service.UserService;
/**
 * Service Implementation for managing Plano.
 */
@Service
@Transactional
public class PlanoServiceImpl implements PlanoService{

    private final Logger log = LoggerFactory.getLogger(PlanoServiceImpl.class);

    private final PlanoRepository planoRepository;
    
    private final PlanoDetalleService planoDetalleService;

    private final TramiteService tramiteService;
    
    private final ArchivoService archivoService;    
    
    private final UserService userService;
    
    private final ProfesionalRepository profesionalRepository;

    public PlanoServiceImpl(PlanoRepository planoRepository, PlanoDetalleService planoDetalleService, TramiteService tramiteService, ArchivoService archivoService, UserService userService, ProfesionalRepository profesionalRepository) {
        this.planoRepository = planoRepository;
        this.planoDetalleService = planoDetalleService;
        this.tramiteService = tramiteService;
        this.archivoService = archivoService;
        this.userService = userService;
        this.profesionalRepository = profesionalRepository;
    }

    

   

	/**
     * Save a plano.
     *
     * @param plano the entity to save
     * @return the persisted entity
     */
    @Override
    public Plano save(Plano plano) {
        log.debug("Request to save Plano : {}", plano);
        return planoRepository.save(plano);
    }

    /**
     * Get all the planos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Plano> findAll() {
        log.debug("Request to get all Planos");
        List<Plano> planos = planoRepository.findAll();
        List<Plano> retorno = new ArrayList<>();
        Optional<User> user = userService.getUserWithAuthorities();
        for (Plano plano : planos) {
            String login = user.get().getLogin();
            if (login.equals(plano.getProfesional().getUsuario().getLogin())) {
                retorno.add(plano);
            }
        }
        return retorno;
    }

    /**
     * Get one plano by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Plano findOne(Long id) {
        log.debug("Request to get Plano : {}", id);
        return planoRepository.findOne(id);
    }

    /**
     * Delete the plano by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Plano : {}", id);
        planoRepository.delete(id);
    }
    
    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////
    @Override
    public Plano saveDTO(PlanoDTO dto) {

        Optional<User> user = userService.getUserWithAuthorities();
        dto.setUsuario(user.get());
        dto.setProfesional(profesionalRepository.findByUsuario(dto.getUsuario()));
        log.debug("Request to save Plano : {}", dto);
        EstadoPlano estadoPlano = null;
        Plano plano = getConvertPlanoFromDTO(dto);

        Plano result = planoRepository.save(plano);

        //// Aca guardo el archivo y el detalle
        PlanoDetalle planoDetalle = new PlanoDetalle();
        Tramite tramite = new Tramite();
        Archivo archivo = new Archivo();

        for (PlanoDetalleDTO detalle : dto.getPlanoDetalles()) {
            detalle.setPlano(result);
            
            detalle.setEstado(estadoPlano.ENTREGADO);

            planoDetalle = planoDetalleService.save(getconvertoPlanoDetalleFromDTO(detalle));

            //// tramite
            tramite = detalle.getTramite();
            tramite.setPlanoDetalle(planoDetalle);
            tramite.setFecha(dto.getFecha());
            tramite = tramiteService.firstSave(tramite);
            /// archivo
            archivo = detalle.getArchivo();
            archivo.setTramite(tramite);
            archivoService.firstSave(archivo);

            planoDetalle = new PlanoDetalle();
            tramite = new Tramite();
            archivo = new Archivo();
            //plano = new Plano();
        }

        return result;
    }
    
    
    public Plano getConvertPlanoFromDTO(PlanoDTO dto) {
    	Plano retorno = new Plano();
    	
    	retorno.setCuitResponsable(dto.getCuitResponsable());
    	retorno.setFecha(dto.getFecha());
    	retorno.setId(dto.getId());
    	retorno.setProfesional(dto.getProfesional());
    	retorno.setResponsable(dto.getResponsable());
    	retorno.setNomeclatura(dto.getNomeclatura());
    	Set<PlanoDetalle> detallePl = new HashSet<>();
    	for (PlanoDetalleDTO detDTO : dto.getPlanoDetalles()) {
			detallePl.add(getconvertoPlanoDetalleFromDTO(detDTO));
		}
    	retorno.setPlanoDetalles(detallePl);
    	
    	return retorno;
    }
    
    
    public PlanoDetalle getconvertoPlanoDetalleFromDTO(PlanoDetalleDTO dto) {
    	PlanoDetalle retorno = new PlanoDetalle();
    	
    	retorno.setEstado(dto.getEstado());
    	retorno.setId(dto.getId());
    	retorno.setPlano(dto.getPlano());
    	retorno.setTipoPlano(dto.getTipoPlano());
    	retorno.addTramites(dto.getTramite());
    	
    	return retorno;
    }
}
