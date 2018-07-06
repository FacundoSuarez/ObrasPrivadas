package municipalidad.obras.service.dto;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import municipalidad.obras.domain.Operador;

/**
 *
 * @author Facundo
 */
public class ArchivoDTO {
    
    private ZonedDateTime fechaFin;
    
    private Operador operador;
    
    private String observaciones;
    
    private Long id;
    
    private byte[] archivo;
    
    private Set<PlanoDetalleDTO> planoDetalles = new HashSet<>();
    
    
    

    public Set<PlanoDetalleDTO> getPlanoDetalles() {
        return planoDetalles;
    }

    public void setPlanoDetalles(Set<PlanoDetalleDTO> planoDetalles) {
        this.planoDetalles = planoDetalles;
    }
    
    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }
    
    public ZonedDateTime getFechaFin() {
        return fechaFin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setFechaFin(ZonedDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
