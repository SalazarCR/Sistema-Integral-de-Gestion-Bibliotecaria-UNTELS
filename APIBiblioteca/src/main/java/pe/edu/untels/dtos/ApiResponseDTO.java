package pe.edu.untels.dtos;

public class ApiResponseDTO {

    private Boolean success;
    private String message;
    private Object data;
    private Integer statusCode;

    public ApiResponseDTO() {}

    public ApiResponseDTO(Boolean success, String message) {
        this.success = success;
        this.message = message;
        this.statusCode = success ? 200 : 400;
    }

    public ApiResponseDTO(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.statusCode = success ? 200 : 400;
    }

    public ApiResponseDTO(Boolean success, String message, Object data, Integer statusCode) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
    }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }

    public Integer getStatusCode() { return statusCode; }
    public void setStatusCode(Integer statusCode) { this.statusCode = statusCode; }

    @Override
    public String toString() {
        return "ApiResponseDTO{" + "success=" + success + ", message='" + message + '\'' + '}';
    }
}

