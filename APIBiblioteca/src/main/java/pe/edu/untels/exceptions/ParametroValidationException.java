package pe.edu.untels.exceptions;

public class ParametroValidationException extends RuntimeException {

    public ParametroValidationException(String message) {
        super(message);
    }

    public ParametroValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

