package pe.edu.untels.dtos;

public class LoginResponseDTO {
    private boolean success;
    private String message;
    private Integer idUsuario;
    private String username;
    private String email;
    private String rol;
    private String estadoAcceso;

    public LoginResponseDTO() {}

    public static LoginResponseDTO ok(int idUsuario, String username, String email, String rol) {
        LoginResponseDTO r = new LoginResponseDTO();
        r.success = true;
        r.message = "Login correcto";
        r.idUsuario = idUsuario;
        r.username = username;
        r.email = email;
        r.rol = rol;
        r.estadoAcceso = "HABILITADO";
        return r;
    }

    public static LoginResponseDTO error(String message) {
        LoginResponseDTO r = new LoginResponseDTO();
        r.success = false;
        r.message = message;
        return r;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getRole() { return rol; }

    public String getEstadoAcceso() { return estadoAcceso; }
    public void setEstadoAcceso(String estadoAcceso) { this.estadoAcceso = estadoAcceso; }
}