package municipalidad.obras.repository;

import municipalidad.obras.domain.Profesional;
import municipalidad.obras.domain.User;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Profesional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfesionalRepository extends JpaRepository<Profesional, Long> {
    
    @Query("select p from Profesional p where p.usuario = :p_usuario")
    Profesional findByUsuario(@Param("p_usuario")User p_usuario);

}
