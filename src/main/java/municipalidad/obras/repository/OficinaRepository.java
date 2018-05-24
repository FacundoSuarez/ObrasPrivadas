package municipalidad.obras.repository;

import municipalidad.obras.domain.Oficina;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Oficina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OficinaRepository extends JpaRepository<Oficina, Long> {

}
