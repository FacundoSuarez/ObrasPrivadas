package municipalidad.obras.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A ContactoProfesional.
 */
@Entity
@Table(name = "contacto_profesional")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContactoProfesional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    private TipoContacto tipoContacto;

    @ManyToOne
    private Profesional profesional;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ContactoProfesional descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoContacto getTipoContacto() {
        return tipoContacto;
    }

    public ContactoProfesional tipoContacto(TipoContacto tipoContacto) {
        this.tipoContacto = tipoContacto;
        return this;
    }

    public void setTipoContacto(TipoContacto tipoContacto) {
        this.tipoContacto = tipoContacto;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public ContactoProfesional profesional(Profesional profesional) {
        this.profesional = profesional;
        return this;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
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
        ContactoProfesional contactoProfesional = (ContactoProfesional) o;
        if (contactoProfesional.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactoProfesional.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactoProfesional{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
