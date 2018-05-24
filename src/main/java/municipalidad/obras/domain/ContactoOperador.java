package municipalidad.obras.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A ContactoOperador.
 */
@Entity
@Table(name = "contacto_operador")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContactoOperador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    private TipoContacto tipoContacto;

    @ManyToOne
    private Operador operador;

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

    public ContactoOperador descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoContacto getTipoContacto() {
        return tipoContacto;
    }

    public ContactoOperador tipoContacto(TipoContacto tipoContacto) {
        this.tipoContacto = tipoContacto;
        return this;
    }

    public void setTipoContacto(TipoContacto tipoContacto) {
        this.tipoContacto = tipoContacto;
    }

    public Operador getOperador() {
        return operador;
    }

    public ContactoOperador operador(Operador operador) {
        this.operador = operador;
        return this;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
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
        ContactoOperador contactoOperador = (ContactoOperador) o;
        if (contactoOperador.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactoOperador.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactoOperador{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
