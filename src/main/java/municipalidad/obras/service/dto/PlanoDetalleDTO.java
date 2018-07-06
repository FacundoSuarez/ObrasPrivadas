package municipalidad.obras.service.dto;

import java.util.HashSet;
import java.util.Set;
import municipalidad.obras.domain.Archivo;
import municipalidad.obras.domain.Plano;
import municipalidad.obras.domain.PlanoDetalle;
import municipalidad.obras.domain.TipoPlano;
import municipalidad.obras.domain.Tramite;
import municipalidad.obras.domain.enumeration.EstadoPlano;

public class PlanoDetalleDTO {

    private Long id;

    private EstadoPlano estado;

    private Tramite tramite = new Tramite();

    private Plano plano;

    private TipoPlano tipoPlano;
    
    private Set<Tramite> tramites = new HashSet<>();
    
    private Archivo archivo;
    
    private PlanoDetalle planoDetalle;
    
    private Set<Archivo> archivos;

    private Set<TramiteDTO> tramitesDTO;

    
    
    
    public Set<TramiteDTO> getTramitesDTO() {
        return tramitesDTO;
    }

    public void setTramitesDTO(Set<TramiteDTO> tramitesDTO) {
        this.tramitesDTO = tramitesDTO;
    }
    
    public Set<Archivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(Set<Archivo> archivos) {
        this.archivos = archivos;
    }
    
    public PlanoDetalle getPlanoDetalle() {
        return planoDetalle;
    }

    public void setPlanoDetalle(PlanoDetalle planoDetalle) {
        this.planoDetalle = planoDetalle;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoPlano getEstado() {
        return estado;
    }

    public void setEstado(EstadoPlano estado) {
        this.estado = estado;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public TipoPlano getTipoPlano() {
        return tipoPlano;
    }

    public void setTipoPlano(TipoPlano tipoPlano) {
        this.tipoPlano = tipoPlano;
    }

    public Set<Tramite> getTramites() {
        return tramites;
    }

    public void setTramites(Set<Tramite> tramites) {
        this.tramites = tramites;
    }

    public Archivo getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

    

}
