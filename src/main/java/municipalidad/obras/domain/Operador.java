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
 * A Operador.
 */
@Entity
@Table(name = "operador")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Operador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private User usuario;

    @OneToOne
    @JoinColumn(unique = true)
    private Oficina oficina;

    @OneToMany(mappedBy = "operador")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContactoOperador> contactos = new HashSet<>();

    @OneToMany(mappedBy = "operador")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tramite> tramites = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUsuario() {
        return usuario;
    }

    public Operador usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public Operador oficina(Oficina oficina) {
        this.oficina = oficina;
        return this;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    public Set<ContactoOperador> getContactos() {
        return contactos;
    }

    public Operador contactos(Set<ContactoOperador> contactoOperadors) {
        this.contactos = contactoOperadors;
        return this;
    }

    public Operador addContactos(ContactoOperador contactoOperador) {
        this.contactos.add(contactoOperador);
        contactoOperador.setOperador(this);
        return this;
    }

    public Operador removeContactos(ContactoOperador contactoOperador) {
        this.contactos.remove(contactoOperador);
        contactoOperador.setOperador(null);
        return this;
    }

    public void setContactos(Set<ContactoOperador> contactoOperadors) {
        this.contactos = contactoOperadors;
    }

    public Set<Tramite> getTramites() {
        return tramites;
    }

    public Operador tramites(Set<Tramite> tramites) {
        this.tramites = tramites;
        return this;
    }

    public Operador addTramites(Tramite tramite) {
        this.tramites.add(tramite);
        tramite.setOperador(this);
        return this;
    }

    public Operador removeTramites(Tramite tramite) {
        this.tramites.remove(tramite);
        tramite.setOperador(null);
        return this;
    }

    public void setTramites(Set<Tramite> tramites) {
        this.tramites = tramites;
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
        Operador operador = (Operador) o;
        if (operador.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operador.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operador{" +
            "id=" + getId() +
            "}";
    }
}
