package municipalidad.obras.repository;

import municipalidad.obras.domain.ContactoOperador;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContactoOperador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactoOperadorRepository extends JpaRepository<ContactoOperador, Long> {

}
