package municipalidad.obras.repository;

import municipalidad.obras.domain.PlanoDetalle;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PlanoDetalle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoDetalleRepository extends JpaRepository<PlanoDetalle, Long> {

}
