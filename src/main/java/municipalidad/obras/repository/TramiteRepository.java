package municipalidad.obras.repository;

import java.util.Set;
import municipalidad.obras.domain.PlanoDetalle;
import municipalidad.obras.domain.Tramite;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Tramite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TramiteRepository extends JpaRepository<Tramite, Long> {
    
    @Query("select a from Tramite a where a.planoDetalle = :p_planoDetalle")
    Set<Tramite> findByPlanoDetalle (@Param("p_planoDetalle")PlanoDetalle planoDetalle);
}
