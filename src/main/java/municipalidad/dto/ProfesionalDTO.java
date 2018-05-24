/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package municipalidad.dto;

import java.util.HashSet;
import java.util.Set;
import municipalidad.obras.domain.Profesional;
import municipalidad.obras.domain.User;

/**
 *
 * @author Facundo
 */
public class ProfesionalDTO {
 
    private Long id;
    private String profesion;
    private String matricula;
    private Set<User> User = new HashSet<>();
    private String descripcion;
    private Set<Profesional> Profesional = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Set<User> getUser() {
        return User;
    }

    public void setUser(Set<User> User) {
        this.User = User;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Profesional> getProfesional() {
        return Profesional;
    }

    public void setProfesional(Set<Profesional> Profesional) {
        this.Profesional = Profesional;
    }

    
    
     
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
