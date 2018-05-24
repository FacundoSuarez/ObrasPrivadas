package municipalidad.dto;

import municipalidad.obras.domain.Archivo;
import municipalidad.obras.domain.Plano;
import municipalidad.obras.domain.TipoPlano;
import municipalidad.obras.domain.Tramite;
import municipalidad.obras.domain.enumeration.EstadoPlano;

public class PlanoDetalleDTO {

    private Long id;

    private EstadoPlano estado;

    private Tramite tramite = new Tramite();

    private Plano plano;

    private TipoPlano tipoPlano;

    private Archivo archivo;

    private Archivo observaciones;

    
    
    
    
    
    
    
    
    
    public Archivo getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(Archivo observaciones) {
        this.observaciones = observaciones;
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

    public Archivo getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

}
