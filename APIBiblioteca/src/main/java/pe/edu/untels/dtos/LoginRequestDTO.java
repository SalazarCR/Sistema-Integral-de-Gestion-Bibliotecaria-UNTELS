package pe.edu.untels.dtos;

public class LoginRequestDTO {
    private String usernameUser;
    private String emailUser;
    private String passwordUser;

    public LoginRequestDTO() {}

    public String getUsernameUser() { return usernameUser; }
    public void setUsernameUser(String usernameUser) { this.usernameUser = usernameUser; }

    public String getEmailUser() { return emailUser; }
    public void setEmailUser(String emailUser) { this.emailUser = emailUser; }

    public String getPasswordUser() { return passwordUser; }
    public void setPasswordUser(String passwordUser) { this.passwordUser = passwordUser; }
}