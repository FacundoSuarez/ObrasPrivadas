package municipalidad.obras.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Profesional.
 */
@Entity
@Table(name = "profesional")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Profesional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profesion")
    private String profesion;

    @Column(name = "matricula")
    private String matricula;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuario;

    @OneToMany(mappedBy = "profesional")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Plano> planos = new HashSet<>();

    @OneToMany(mappedBy = "profesional")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContactoProfesional> contactos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfesion() {
        return profesion;
    }

    public Profesional profesion(String profesion) {
        this.profesion = profesion;
        return this;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getMatricula() {
        return matricula;
    }

    public Profesional matricula(String matricula) {
        this.matricula = matricula;
        return this;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public User getUsuario() {
        return usuario;
    }

    public Profesional usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Set<Plano> getPlanos() {
        return planos;
    }

    public Profesional planos(Set<Plano> planos) {
        this.planos = planos;
        return this;
    }

    public Profesional addPlanos(Plano plano) {
        this.planos.add(plano);
        plano.setProfesional(this);
        return this;
    }

    public Profesional removePlanos(Plano plano) {
        this.planos.remove(plano);
        plano.setProfesional(null);
        return this;
    }

    public void setPlanos(Set<Plano> planos) {
        this.planos = planos;
    }

    public Set<ContactoProfesional> getContactos() {
        return contactos;
    }

    public Profesional contactos(Set<ContactoProfesional> contactoProfesionals) {
        this.contactos = contactoProfesionals;
        return this;
    }

    public Profesional addContactos(ContactoProfesional contactoProfesional) {
        this.contactos.add(contactoProfesional);
        contactoProfesional.setProfesional(this);
        return this;
    }

    public Profesional removeContactos(ContactoProfesional contactoProfesional) {
        this.contactos.remove(contactoProfesional);
        contactoProfesional.setProfesional(null);
        return this;
    }

    public void setContactos(Set<ContactoProfesional> contactoProfesionals) {
        this.contactos = contactoProfesionals;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Profesional profesional = (Profesional) o;
        if (profesional.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profesional.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Profesional{" +
            "id=" + getId() +
            ", profesion='" + getProfesion() + "'" +
            ", matricula='" + getMatricula() + "'" +
            "}";
    }
}
