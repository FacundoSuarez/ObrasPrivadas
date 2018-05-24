package municipalidad.obras.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String PROFESIONAL = "ROLE_PROFESIONAL";
    
    public static final String OPERADOR = "ROLE_OPERADOR";
    
    public static final String DIRECTOR = "ROLE_DIRECTOR";
    
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
    
}
