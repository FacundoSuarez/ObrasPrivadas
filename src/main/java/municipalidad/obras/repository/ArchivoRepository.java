package municipalidad.obras.repository;


import java.util.Set;
import municipalidad.obras.domain.Archivo;
import municipalidad.obras.domain.Tramite;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Archivo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {
    @Query("select a from Archivo a where a.tramite = :p_tramite")
    Set<Archivo> findByTramite(@Param("p_tramite") Tramite p_tramite);
}
