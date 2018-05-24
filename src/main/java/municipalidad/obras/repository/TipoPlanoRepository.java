package municipalidad.obras.repository;

import municipalidad.obras.domain.TipoPlano;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TipoPlano entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoPlanoRepository extends JpaRepository<TipoPlano, Long> {

}
