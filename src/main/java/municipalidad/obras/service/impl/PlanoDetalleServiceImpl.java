package municipalidad.obras.service.impl;

import java.util.HashSet;
import java.util.Set;
import municipalidad.obras.domain.Archivo;
import municipalidad.obras.service.dto.PlanoDetalleDTO;
import municipalidad.obras.service.PlanoDetalleService;
import municipalidad.obras.domain.PlanoDetalle;
import municipalidad.obras.domain.Tramite;
import municipalidad.obras.repository.ArchivoRepository;
import municipalidad.obras.repository.PlanoDetalleRepository;
import municipalidad.obras.repository.TramiteRepository;
import municipalidad.obras.service.dto.TramiteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing PlanoDetalle.
 */
@Service
@Transactional
public class PlanoDetalleServiceImpl implements PlanoDetalleService {

    private final Logger log = LoggerFactory.getLogger(PlanoDetalleServiceImpl.class);

    private final PlanoDetalleRepository planoDetalleRepository;

    private final TramiteRepository tramiteRepository;

    private final ArchivoRepository archivoRepository;

    public PlanoDetalleServiceImpl(PlanoDetalleRepository planoDetalleRepository, TramiteRepository tramiteRepository, ArchivoRepository archivoRepository) {
        this.planoDetalleRepository = planoDetalleRepository;
        this.tramiteRepository = tramiteRepository;
        this.archivoRepository = archivoRepository;
    }

    /**
     * Save a planoDetalle.
     *
     * @param planoDetalle the entity to save
     * @return the persisted entity
     */
    @Override
    public PlanoDetalle save(PlanoDetalle planoDetalle) {
        log.debug("Request to save PlanoDetalle : {}", planoDetalle);
        return planoDetalleRepository.save(planoDetalle);
    }

    /**
     * Get all the planoDetalles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PlanoDetalle> findAll(Pageable pageable) {
        log.debug("Request to get all PlanoDetalles");
        return planoDetalleRepository.findAll(pageable);
    }

    /**
     * Get one planoDetalle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PlanoDetalle findOne(Long id) {
        log.debug("Request to get PlanoDetalle : {}", id);
        return planoDetalleRepository.findOne(id);
    }

    /**
     * Delete the planoDetalle by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanoDetalle : {}", id);
        planoDetalleRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public PlanoDetalleDTO findOneDTO(Long id) {
        log.debug("Request to get PlanoDetalleDTO : {}", id);
        PlanoDetalleDTO retorno = new PlanoDetalleDTO();
        PlanoDetalle planoDetalle = new PlanoDetalle();
        Set<TramiteDTO> tramiteDTO = new HashSet<>();
        Set<Archivo> archivo = new HashSet<>();

        planoDetalle = planoDetalleRepository.findOne(id);

        retorno.setId(planoDetalle.getId());
        retorno.setPlano(planoDetalle.getPlano());
        retorno.setTipoPlano(planoDetalle.getTipoPlano());
        retorno.setEstado(planoDetalle.getEstado());
        Set<Tramite> tramiteList = tramiteRepository.findByPlanoDetalle(planoDetalle);

        for (Tramite tramite1 : tramiteList) {
            tramiteDTO.add(getConvertTramiteToDTO(tramite1));
            for (TramiteDTO tramiteDTO1 : tramiteDTO) {
                archivo = archivoRepository.findByTramite(tramite1);
                tramiteDTO1.setArchivos(archivo);
                
            }
            retorno.setTramitesDTO(tramiteDTO);
        }
        return retorno;
    }

    public TramiteDTO getConvertTramiteToDTO(Tramite tramite) {
        TramiteDTO retorno = new TramiteDTO();

        retorno.setId(tramite.getId());
        retorno.setFecha(tramite.getFechaFin());
        retorno.setFechaFin(tramite.getFechaFin());
        retorno.setObservaciones(tramite.getObservaciones());
        retorno.setPlanoDetalle(tramite.getPlanoDetalle());

        return retorno;
    }

}
