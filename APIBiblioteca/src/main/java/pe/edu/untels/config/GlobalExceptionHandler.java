package pe.edu.untels.config;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pe.edu.untels.dtos.ApiResponseDTO;
import pe.edu.untels.exceptions.AuthException;
import pe.edu.untels.exceptions.ParametroValidationException;
import pe.edu.untels.exceptions.ValidationException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(ParametroValidationException.class)
    public ResponseEntity<ApiResponseDTO> handleParametroValidationException(ParametroValidationException ex) {

        log.warn("Validation exception: {}", ex.getMessage());

        ApiResponseDTO response = new ApiResponseDTO(
                false,
                ex.getMessage(),
                null,
                400
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        log.warn("Validation error in request");

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponseDTO response = new ApiResponseDTO(
                false,
                "Error de validación en los datos",
                errors,
                400
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponseDTO> handleValidationException(ValidationException ex) {

        log.warn("ValidationException: {}", ex.getMessage());

        ApiResponseDTO response = new ApiResponseDTO(
                false,
                ex.getMessage(),
                null,
                400
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponseDTO> handleAuthException(AuthException ex) {

        log.warn("AuthException: {}", ex.getMessage());

        ApiResponseDTO response = new ApiResponseDTO(
                false,
                ex.getMessage(),
                null,
                401
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO> handleGenericException(Exception ex) {

        log.error("Unexpected error occurred", ex);

        ApiResponseDTO response = new ApiResponseDTO(
                false,
                "Error interno del servidor",
                ex.getMessage(),
                500
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}