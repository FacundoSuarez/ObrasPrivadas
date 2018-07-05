package municipalidad.obras.repository;

import java.util.Set;
import municipalidad.obras.domain.Plano;
import municipalidad.obras.domain.PlanoDetalle;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;



/**
 * Spring Data JPA repository for the PlanoDetalle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoDetalleRepository extends JpaRepository<PlanoDetalle, Long> {
    
    @Query("select a from PlanoDetalle a where a.plano = :p_plano")
    Set<PlanoDetalle> findByPlano (@Param("p_plano") Plano p_plano);

}
