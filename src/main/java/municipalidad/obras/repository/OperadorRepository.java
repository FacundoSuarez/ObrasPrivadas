package municipalidad.obras.repository;

import municipalidad.obras.domain.Operador;
import municipalidad.obras.domain.User;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Operador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperadorRepository extends JpaRepository<Operador, Long> {
    
    @Query("select p from Operador p where p.usuario = :p_usuario")
    Operador findByUsuario(@Param("p_usuario")User p_usuario);

}
