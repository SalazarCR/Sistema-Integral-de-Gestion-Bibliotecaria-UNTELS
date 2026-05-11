package pe.edu.untels.exceptions;
/**
 * Excepción para errores de autenticación y autorización.
 */
public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }
}

