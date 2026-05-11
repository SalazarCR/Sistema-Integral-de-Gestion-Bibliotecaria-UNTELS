package pe.edu.untels.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.edu.untels.dtos.ApiResponseDTO;
import pe.edu.untels.exceptions.AuthException;
import pe.edu.untels.exceptions.ParametroValidationException;
import pe.edu.untels.exceptions.ValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e) {
        ApiResponseDTO response = new ApiResponseDTO(false, e.getMessage(), null, 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleAuthException(AuthException e) {
        ApiResponseDTO response = new ApiResponseDTO(false, e.getMessage(), null, 401);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ParametroValidationException.class)
    public ResponseEntity<?> handleParametroValidationException(ParametroValidationException e) {
        ApiResponseDTO response = new ApiResponseDTO(false, e.getMessage(), null, 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception e) {
        ApiResponseDTO response = new ApiResponseDTO(false, "Error interno del servidor", null, 500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
