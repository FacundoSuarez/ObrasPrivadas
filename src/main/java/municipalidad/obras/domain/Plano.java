package municipalidad.obras.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Plano.
 */
@Entity
@Table(name = "plano")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Plano implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private ZonedDateTime fecha;

    @Column(name = "cuit_responsable")
    private Long cuitResponsable;

    @Column(name = "responsable")
    private String responsable;

    @Column(name = "nomeclatura")
    private String nomeclatura;

    @OneToMany(mappedBy = "plano")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlanoDetalle> planoDetalles = new HashSet<>();

    @ManyToOne
    private Profesional profesional;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public Plano fecha(ZonedDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public Long getCuitResponsable() {
        return cuitResponsable;
    }

    public Plano cuitResponsable(Long cuitResponsable) {
        this.cuitResponsable = cuitResponsable;
        return this;
    }

    public void setCuitResponsable(Long cuitResponsable) {
        this.cuitResponsable = cuitResponsable;
    }

    public String getResponsable() {
        return responsable;
    }

    public Plano responsable(String responsable) {
        this.responsable = responsable;
        return this;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getNomeclatura() {
        return nomeclatura;
    }

    public Plano nomeclatura(String nomeclatura) {
        this.nomeclatura = nomeclatura;
        return this;
    }

    public void setNomeclatura(String nomeclatura) {
        this.nomeclatura = nomeclatura;
    }

    public Set<PlanoDetalle> getPlanoDetalles() {
        return planoDetalles;
    }

    public Plano planoDetalles(Set<PlanoDetalle> planoDetalles) {
        this.planoDetalles = planoDetalles;
        return this;
    }

    public Plano addPlanoDetalles(PlanoDetalle planoDetalle) {
        this.planoDetalles.add(planoDetalle);
        planoDetalle.setPlano(this);
        return this;
    }

    public Plano removePlanoDetalles(PlanoDetalle planoDetalle) {
        this.planoDetalles.remove(planoDetalle);
        planoDetalle.setPlano(null);
        return this;
    }

    public void setPlanoDetalles(Set<PlanoDetalle> planoDetalles) {
        this.planoDetalles = planoDetalles;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public Plano profesional(Profesional profesional) {
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
        Plano plano = (Plano) o;
        if (plano.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plano.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Plano{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", cuitResponsable=" + getCuitResponsable() +
            ", responsable='" + getResponsable() + "'" +
            ", nomeclatura='" + getNomeclatura() + "'" +
            "}";
    }
}
