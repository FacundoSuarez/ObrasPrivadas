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
 * A TipoPlano.
 */
@Entity
@Table(name = "tipo_plano")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoPlano implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "tipoPlano")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlanoDetalle> planoDetalles = new HashSet<>();

    @OneToOne(mappedBy = "tipoPlano")
    @JsonIgnore
    private Oficina oficina;

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

    public TipoPlano descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<PlanoDetalle> getPlanoDetalles() {
        return planoDetalles;
    }

    public TipoPlano planoDetalles(Set<PlanoDetalle> planoDetalles) {
        this.planoDetalles = planoDetalles;
        return this;
    }

    public TipoPlano addPlanoDetalles(PlanoDetalle planoDetalle) {
        this.planoDetalles.add(planoDetalle);
        planoDetalle.setTipoPlano(this);
        return this;
    }

    public TipoPlano removePlanoDetalles(PlanoDetalle planoDetalle) {
        this.planoDetalles.remove(planoDetalle);
        planoDetalle.setTipoPlano(null);
        return this;
    }

    public void setPlanoDetalles(Set<PlanoDetalle> planoDetalles) {
        this.planoDetalles = planoDetalles;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public TipoPlano oficina(Oficina oficina) {
        this.oficina = oficina;
        return this;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
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
        TipoPlano tipoPlano = (TipoPlano) o;
        if (tipoPlano.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoPlano.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoPlano{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
