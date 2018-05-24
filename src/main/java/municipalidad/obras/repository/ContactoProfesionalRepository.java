package municipalidad.obras.repository;

import municipalidad.obras.domain.ContactoProfesional;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ContactoProfesional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactoProfesionalRepository extends JpaRepository<ContactoProfesional, Long> {

}
