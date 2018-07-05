package municipalidad.obras.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.print.attribute.HashAttributeSet;

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
import municipalidad.obras.repository.ArchivoRepository;
import municipalidad.obras.repository.PlanoDetalleRepository;
import municipalidad.obras.repository.PlanoRepository;
import municipalidad.obras.repository.ProfesionalRepository;
import municipalidad.obras.repository.TramiteRepository;
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
public class PlanoServiceImpl implements PlanoService {

    private final Logger log = LoggerFactory.getLogger(PlanoServiceImpl.class);

    private final PlanoRepository planoRepository;

    private final PlanoDetalleService planoDetalleService;

    private final PlanoDetalleRepository planoDetalleRepository;

    private final TramiteRepository tramiteRepository;

    private final TramiteService tramiteService;

    private final ArchivoService archivoService;

    private final ArchivoRepository archivoRepository;

    private final UserService userService;

    private final ProfesionalRepository profesionalRepository;

    public PlanoServiceImpl(PlanoRepository planoRepository, PlanoDetalleService planoDetalleService, PlanoDetalleRepository planoDetalleRepository, TramiteRepository tramiteRepository, TramiteService tramiteService, ArchivoService archivoService, ArchivoRepository archivoRepository, UserService userService, ProfesionalRepository profesionalRepository) {
        this.planoRepository = planoRepository;
        this.planoDetalleService = planoDetalleService;
        this.planoDetalleRepository = planoDetalleRepository;
        this.tramiteRepository = tramiteRepository;
        this.tramiteService = tramiteService;
        this.archivoService = archivoService;
        this.archivoRepository = archivoRepository;
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
     * Delete the plano by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {

        log.debug("Request to delete Plano : {}", id);
        planoRepository.delete(id);
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
    public PlanoDTO findOneDTO(Long id) {

        PlanoDTO planoDTO = new PlanoDTO();
        Plano plano = planoRepository.findOne(id);

        planoDTO.setId(plano.getId());
        planoDTO.setFecha(plano.getFecha());
        planoDTO.setCuitResponsable(plano.getCuitResponsable());
        planoDTO.setResponsable(plano.getResponsable());
        planoDTO.setProfesional(plano.getProfesional());
        planoDTO.setPlanoDetalleSet(planoDetalleRepository.findByPlano(plano));

        Set<Tramite> tramites = new HashSet<>();
        Set<Tramite> tramitesPlanoDetalle = new HashSet<>();
        for (PlanoDetalle detalle : planoDTO.getPlanoDetalleSet()) {
            tramitesPlanoDetalle = tramiteRepository.findByPlanoDetalle(detalle);
            for (Tramite tramitesPlanoDetalle1 : tramitesPlanoDetalle) {
                tramites.add(tramitesPlanoDetalle1);
            }
        }
        planoDTO.setTramites(tramites);

        Set<Archivo> archivosTramite = new HashSet<>();
        Set<Archivo> archivos = new HashSet<>();
        for (Tramite tramiteDet : planoDTO.getTramites()) {
            archivosTramite = archivoRepository.findByTramite(tramiteDet);
            for (Archivo archivosTramite1 : archivosTramite) {
                archivos.add(archivosTramite1);
            }
        }
        planoDTO.setArchivos(archivos);
        return planoDTO;
    }

    public PlanoDetalleDTO getPlanoDetalleToDTO(PlanoDetalle plano) {
        PlanoDetalleDTO retorno = new PlanoDetalleDTO();
        retorno.setId(plano.getId());
        retorno.setEstado(plano.getEstado());
        retorno.setPlano(plano.getPlano());
        retorno.setTipoPlano(plano.getTipoPlano());
        return retorno;
    }

    /////////////////////////////////////////////////////////
    /*                       GUARDA                        */
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

    @Override
    @Transactional(readOnly = true)
    public Plano findOne(Long id) {
        log.debug("Request to get Plano : {}", id);
        return planoRepository.findOne(id);
    }
}
