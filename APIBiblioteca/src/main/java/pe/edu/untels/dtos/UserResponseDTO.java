package pe.edu.untels.dtos;

import java.time.LocalDateTime;

public class UserResponseDTO {

    private Integer idUser;
    private String usernameUser;
    private String emailUser;
    private String dniUser;
    private String nombreUser;
    private String apellidoUser;
    private String rol;
    private Boolean statusUser;
    private LocalDateTime createdAt;

    public UserResponseDTO() {}

    public Integer getIdUser() { return idUser; }
    public void setIdUser(Integer idUser) { this.idUser = idUser; }

    public String getUsernameUser() { return usernameUser; }
    public void setUsernameUser(String usernameUser) { this.usernameUser = usernameUser; }

    public String getEmailUser() { return emailUser; }
    public void setEmailUser(String emailUser) { this.emailUser = emailUser; }

    public String getDniUser() { return dniUser; }
    public void setDniUser(String dniUser) { this.dniUser = dniUser; }

    public String getNombreUser() { return nombreUser; }
    public void setNombreUser(String nombreUser) { this.nombreUser = nombreUser; }

    public String getApellidoUser() { return apellidoUser; }
    public void setApellidoUser(String apellidoUser) { this.apellidoUser = apellidoUser; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Boolean getStatusUser() { return statusUser; }
    public void setStatusUser(Boolean statusUser) { this.statusUser = statusUser; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
