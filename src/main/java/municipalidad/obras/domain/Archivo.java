package municipalidad.obras.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A Archivo.
 */
@Entity
@Table(name = "archivo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Archivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "archivo")
    private byte[] archivo;

    @Column(name = "archivo_content_type")
    private String archivoContentType;

    @ManyToOne
    private Tramite tramite;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public Archivo archivo(byte[] archivo) {
        this.archivo = archivo;
        return this;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getArchivoContentType() {
        return archivoContentType;
    }

    public Archivo archivoContentType(String archivoContentType) {
        this.archivoContentType = archivoContentType;
        return this;
    }

    public void setArchivoContentType(String archivoContentType) {
        this.archivoContentType = archivoContentType;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public Archivo tramite(Tramite tramite) {
        this.tramite = tramite;
        return this;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
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
        Archivo archivo = (Archivo) o;
        if (archivo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), archivo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Archivo{" +
            "id=" + getId() +
            ", archivo='" + getArchivo() + "'" +
            ", archivoContentType='" + getArchivoContentType() + "'" +
            "}";
    }
}
