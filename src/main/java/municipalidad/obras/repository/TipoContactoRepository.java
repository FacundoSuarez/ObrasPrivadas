package municipalidad.obras.repository;

import municipalidad.obras.domain.TipoContacto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TipoContacto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoContactoRepository extends JpaRepository<TipoContacto, Long> {

}
