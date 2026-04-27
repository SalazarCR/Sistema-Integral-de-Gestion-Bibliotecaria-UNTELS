package pe.edu.untels.dtos;

public class LoginRequestDTO {
    private String usernameUser;
    private String passwordUser;

    public LoginRequestDTO() {}
    public LoginRequestDTO(String usernameUser, String passwordUser) {
        this.usernameUser = usernameUser;
        this.passwordUser = passwordUser;
    }

    public String getUsernameUser() { return usernameUser; }
    public void setUsernameUser(String usernameUser) { this.usernameUser = usernameUser; }
    public String getPasswordUser() { return passwordUser; }
    public void setPasswordUser(String passwordUser) { this.passwordUser = passwordUser; }
}

