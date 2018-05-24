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
 * A TipoContacto.
 */
@Entity
@Table(name = "tipo_contacto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoContacto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "tipoContacto")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContactoOperador> contactoOperadores = new HashSet<>();

    @OneToMany(mappedBy = "tipoContacto")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContactoProfesional> contactoProfesionales = new HashSet<>();

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

    public TipoContacto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<ContactoOperador> getContactoOperadores() {
        return contactoOperadores;
    }

    public TipoContacto contactoOperadores(Set<ContactoOperador> contactoOperadors) {
        this.contactoOperadores = contactoOperadors;
        return this;
    }

    public TipoContacto addContactoOperadores(ContactoOperador contactoOperador) {
        this.contactoOperadores.add(contactoOperador);
        contactoOperador.setTipoContacto(this);
        return this;
    }

    public TipoContacto removeContactoOperadores(ContactoOperador contactoOperador) {
        this.contactoOperadores.remove(contactoOperador);
        contactoOperador.setTipoContacto(null);
        return this;
    }

    public void setContactoOperadores(Set<ContactoOperador> contactoOperadors) {
        this.contactoOperadores = contactoOperadors;
    }

    public Set<ContactoProfesional> getContactoProfesionales() {
        return contactoProfesionales;
    }

    public TipoContacto contactoProfesionales(Set<ContactoProfesional> contactoProfesionals) {
        this.contactoProfesionales = contactoProfesionals;
        return this;
    }

    public TipoContacto addContactoProfesionales(ContactoProfesional contactoProfesional) {
        this.contactoProfesionales.add(contactoProfesional);
        contactoProfesional.setTipoContacto(this);
        return this;
    }

    public TipoContacto removeContactoProfesionales(ContactoProfesional contactoProfesional) {
        this.contactoProfesionales.remove(contactoProfesional);
        contactoProfesional.setTipoContacto(null);
        return this;
    }

    public void setContactoProfesionales(Set<ContactoProfesional> contactoProfesionals) {
        this.contactoProfesionales = contactoProfesionals;
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
        TipoContacto tipoContacto = (TipoContacto) o;
        if (tipoContacto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoContacto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoContacto{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
