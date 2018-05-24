package municipalidad.obras.repository;

import municipalidad.obras.domain.Tramite;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tramite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TramiteRepository extends JpaRepository<Tramite, Long> {

}
