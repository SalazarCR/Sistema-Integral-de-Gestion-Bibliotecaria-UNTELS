package pe.edu.untels.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO de respuesta para el login, contiene tokens y datos básicos del usuario.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseDTO {

    private Boolean success;
    private String message;
    private Object data;
    private Integer statusCode;
    private LocalDateTime timestamp;

    // Constructores adicionales para compatibilidad con código existente
    public ApiResponseDTO(Boolean success, String message) {
        this.success = success;
        this.message = message;
        this.statusCode = success ? 200 : 400;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponseDTO(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.statusCode = success ? 200 : 400;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponseDTO(Boolean success, String message, Object data, Integer statusCode) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
        this.timestamp = LocalDateTime.now();
    }
}

