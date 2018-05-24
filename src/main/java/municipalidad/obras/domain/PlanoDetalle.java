package municipalidad.obras.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import municipalidad.obras.domain.enumeration.EstadoPlano;


/**
 * A PlanoDetalle.
 */
@Entity
@Table(name = "plano_detalle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlanoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPlano estado;

    @OneToMany(mappedBy = "planoDetalle")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tramite> tramites = new HashSet<>();

    @ManyToOne
    private Plano plano;

    @ManyToOne
    private TipoPlano tipoPlano;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoPlano getEstado() {
        return estado;
    }

    public PlanoDetalle estado(EstadoPlano estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoPlano estado) {
        this.estado = estado;
    }

    public Set<Tramite> getTramites() {
        return tramites;
    }

    public PlanoDetalle tramites(Set<Tramite> tramites) {
        this.tramites = tramites;
        return this;
    }

    public PlanoDetalle addTramites(Tramite tramite) {
        this.tramites.add(tramite);
        tramite.setPlanoDetalle(this);
        return this;
    }

    public PlanoDetalle removeTramites(Tramite tramite) {
        this.tramites.remove(tramite);
        tramite.setPlanoDetalle(null);
        return this;
    }

    public void setTramites(Set<Tramite> tramites) {
        this.tramites = tramites;
    }

    public Plano getPlano() {
        return plano;
    }

    public PlanoDetalle plano(Plano plano) {
        this.plano = plano;
        return this;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public TipoPlano getTipoPlano() {
        return tipoPlano;
    }

    public PlanoDetalle tipoPlano(TipoPlano tipoPlano) {
        this.tipoPlano = tipoPlano;
        return this;
    }

    public void setTipoPlano(TipoPlano tipoPlano) {
        this.tipoPlano = tipoPlano;
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
        PlanoDetalle planoDetalle = (PlanoDetalle) o;
        if (planoDetalle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planoDetalle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlanoDetalle{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
