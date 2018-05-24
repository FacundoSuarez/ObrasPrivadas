package municipalidad.dto;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import municipalidad.obras.domain.Archivo;
import municipalidad.obras.domain.Operador;
import municipalidad.obras.domain.PlanoDetalle;

/**
 *
 * @author Facundo
 */
public class TramiteDTO {
    
    private Long id;
    
    private ZonedDateTime fecha;

    private ZonedDateTime fechaFin;

    private String observaciones;

    private Set<Archivo> archivos = new HashSet<>();

    private PlanoDetalle planoDetalle;

    private Operador operador;

    
    
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public ZonedDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(ZonedDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }
    
    
    
}

