package pe.edu.untels.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

/**
 * DTO genérico para respuestas de API
 * Proporciona una estructura consistente para todas las respuestas HTTP
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDTO<T> {
    private boolean success;
    private String message;
    private T data;
    private String error;
    private int status;
    private LocalDateTime timestamp;

    public ApiResponseDTO() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponseDTO(boolean success, String message, T data, int status) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponseDTO(boolean success, String message, String error, int status) {
        this.success = success;
        this.message = message;
        this.error = error;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    // Factory methods
    public static <T> ApiResponseDTO<T> ok(String message, T data, int status) {
        return new ApiResponseDTO<>(true, message, data, status);
    }

    public static <T> ApiResponseDTO<T> ok(String message, T data) {
        return new ApiResponseDTO<>(true, message, data, 200);
    }

    public static <T> ApiResponseDTO<T> ok(T data) {
        return new ApiResponseDTO<>(true, "Éxito", data, 200);
    }

    public static <T> ApiResponseDTO<T> error(String message, String error, int status) {
        return new ApiResponseDTO<>(false, message, error, status);
    }

    public static <T> ApiResponseDTO<T> error(String message, int status) {
        return new ApiResponseDTO<>(false, message, message, status);
    }

    public static <T> ApiResponseDTO<T> created(String message, T data) {
        return new ApiResponseDTO<>(true, message, data, 201);
    }

    public static <T> ApiResponseDTO<T> noContent(String message) {
        return new ApiResponseDTO<>(true, message, null, 204);
    }

    public static <T> ApiResponseDTO<T> unauthorized(String message) {
        return new ApiResponseDTO<>(false, message, "No autorizado", 401);
    }

    public static <T> ApiResponseDTO<T> forbidden(String message) {
        return new ApiResponseDTO<>(false, message, "Acceso denegado", 403);
    }

    public static <T> ApiResponseDTO<T> notFound(String message) {
        return new ApiResponseDTO<>(false, message, "No encontrado", 404);
    }

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

