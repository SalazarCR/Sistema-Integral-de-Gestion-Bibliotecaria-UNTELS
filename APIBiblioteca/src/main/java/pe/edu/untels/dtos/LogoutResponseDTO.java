package pe.edu.untels.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogoutResponseDTO {
    private boolean success;
    private String message;
    private int status;
    private String sessionEndTime;

    public LogoutResponseDTO(boolean success, String message, int status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }

    public LogoutResponseDTO(boolean success, String message, int status, String sessionEndTime) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.sessionEndTime = sessionEndTime;
    }

    // Factory methods
    public static LogoutResponseDTO success(String message) {
        return new LogoutResponseDTO(true, message, 200);
    }

    public static LogoutResponseDTO success(String message, String sessionEndTime) {
        return new LogoutResponseDTO(true, message, 200, sessionEndTime);
    }

    public static LogoutResponseDTO error(String message, int status) {
        return new LogoutResponseDTO(false, message, status);
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSessionEndTime() {
        return sessionEndTime;
    }

    public void setSessionEndTime(String sessionEndTime) {
        this.sessionEndTime = sessionEndTime;
    }
}

