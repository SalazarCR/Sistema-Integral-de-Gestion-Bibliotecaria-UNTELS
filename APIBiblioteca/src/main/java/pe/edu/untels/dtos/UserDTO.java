package pe.edu.untels.dtos;

public class UserDTO {

    private String usernameUser;
    private String passwordUser;
    private String emailUser;
    private String dniUser;
    private String nombreUser;
    private String apellidoUser;
    private Integer idRole;

    public UserDTO() {}

    public String getUsernameUser() { return usernameUser; }
    public void setUsernameUser(String usernameUser) { this.usernameUser = usernameUser; }

    public String getPasswordUser() { return passwordUser; }
    public void setPasswordUser(String passwordUser) { this.passwordUser = passwordUser; }

    public String getEmailUser() { return emailUser; }
    public void setEmailUser(String emailUser) { this.emailUser = emailUser; }

    public String getDniUser() { return dniUser; }
    public void setDniUser(String dniUser) { this.dniUser = dniUser; }

    public String getNombreUser() { return nombreUser; }
    public void setNombreUser(String nombreUser) { this.nombreUser = nombreUser; }

    public String getApellidoUser() { return apellidoUser; }
    public void setApellidoUser(String apellidoUser) { this.apellidoUser = apellidoUser; }

    public Integer getIdRole() { return idRole; }
    public void setIdRole(Integer idRole) { this.idRole = idRole; }
}
