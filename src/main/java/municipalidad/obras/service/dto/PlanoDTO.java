/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package municipalidad.obras.service.dto;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import municipalidad.obras.domain.Archivo;
import municipalidad.obras.domain.PlanoDetalle;

import municipalidad.obras.domain.Profesional;
import municipalidad.obras.domain.Tramite;
import municipalidad.obras.domain.User;
import org.terracotta.offheapstore.util.AATreeSet;

/**
 *
 * @author Facundo
 */
public class PlanoDTO {
    
    
    private Long id;

    private ZonedDateTime fecha;

    private Long cuitResponsable;

    private String responsable;

    private Set<PlanoDetalleDTO> planoDetalles = new HashSet<>();

    private Profesional profesional;
    
    private User usuario;
    
    private String nomeclatura;
    
    private Set<Tramite> tramites = new HashSet<>();

    private Set<Archivo> archivos = new HashSet<>();
    
    private PlanoDetalle planoDetalle;
    
    private Set<PlanoDetalle> planoDetalleSet = new HashSet<>();

    
    
    
    
    
    
    public Set<PlanoDetalle> getPlanoDetalleSet() {
        return planoDetalleSet;
    }

    public void setPlanoDetalleSet(Set<PlanoDetalle> planoDetalleSet) {
        this.planoDetalleSet = planoDetalleSet;
    }
    
    public PlanoDetalle getPlanoDetalle() {
        return planoDetalle;
    }

    public void setPlanoDetalle(PlanoDetalle planoDetalle) {
        this.planoDetalle = planoDetalle;
    }
    
    public Set<Archivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(Set<Archivo> archivos) {
        this.archivos = archivos;
    }
    
    
    public Set<Tramite> getTramites() {
        return tramites;
    }

    public void setTramites(Set<Tramite> tramites) {
        this.tramites = tramites;
    }
    
    
    public String getNomeclatura() {
        return nomeclatura;
    }

    public void setNomeclatura(String nomeclatura) {
        this.nomeclatura = nomeclatura;
    }
    
    public Long getId() {
        return id;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
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

    public Long getCuitResponsable() {
        return cuitResponsable;
    }

    public void setCuitResponsable(Long cuitResponsable) {
        this.cuitResponsable = cuitResponsable;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Set<PlanoDetalleDTO> getPlanoDetalles() {
        return planoDetalles;
    }

    public void setPlanoDetalles(Set<PlanoDetalleDTO> planoDetalles) {
        this.planoDetalles = planoDetalles;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }
}
