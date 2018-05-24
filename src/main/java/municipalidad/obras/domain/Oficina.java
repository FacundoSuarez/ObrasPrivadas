package municipalidad.obras.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A Oficina.
 */
@Entity
@Table(name = "oficina")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Oficina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profesional")
    private String profesional;

    @Column(name = "correcciones")
    private String correcciones;

    @OneToOne
    @JoinColumn(unique = true)
    private TipoPlano tipoPlano;

    @OneToOne(mappedBy = "oficina")
    @JsonIgnore
    private Operador operador;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfesional() {
        return profesional;
    }

    public Oficina profesional(String profesional) {
        this.profesional = profesional;
        return this;
    }

    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

    public String getCorrecciones() {
        return correcciones;
    }

    public Oficina correcciones(String correcciones) {
        this.correcciones = correcciones;
        return this;
    }

    public void setCorrecciones(String correcciones) {
        this.correcciones = correcciones;
    }

    public TipoPlano getTipoPlano() {
        return tipoPlano;
    }

    public Oficina tipoPlano(TipoPlano tipoPlano) {
        this.tipoPlano = tipoPlano;
        return this;
    }

    public void setTipoPlano(TipoPlano tipoPlano) {
        this.tipoPlano = tipoPlano;
    }

    public Operador getOperador() {
        return operador;
    }

    public Oficina operador(Operador operador) {
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
        Oficina oficina = (Oficina) o;
        if (oficina.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), oficina.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Oficina{" +
            "id=" + getId() +
            ", profesional='" + getProfesional() + "'" +
            ", correcciones='" + getCorrecciones() + "'" +
            "}";
    }
}
