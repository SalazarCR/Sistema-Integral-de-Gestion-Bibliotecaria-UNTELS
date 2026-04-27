package pe.edu.untels.dtos;

public class LoginResponseDTO {
    private int idUser;
    private String usernameUser;
    private String emailUser;
    private String roleNameRole;
    private boolean statusUser;

    public LoginResponseDTO() {}
    public LoginResponseDTO(int idUser, String usernameUser, String emailUser, String roleNameRole, boolean statusUser) {
        this.idUser = idUser;
        this.usernameUser = usernameUser;
        this.emailUser = emailUser;
        this.roleNameRole = roleNameRole;
        this.statusUser = statusUser;
    }

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
    public String getUsernameUser() { return usernameUser; }
    public void setUsernameUser(String usernameUser) { this.usernameUser = usernameUser; }
    public String getEmailUser() { return emailUser; }
    public void setEmailUser(String emailUser) { this.emailUser = emailUser; }
    public String getRoleNameRole() { return roleNameRole; }
    public void setRoleNameRole(String roleNameRole) { this.roleNameRole = roleNameRole; }
    public boolean isStatusUser() { return statusUser; }
    public void setStatusUser(boolean statusUser) { this.statusUser = statusUser; }
}

