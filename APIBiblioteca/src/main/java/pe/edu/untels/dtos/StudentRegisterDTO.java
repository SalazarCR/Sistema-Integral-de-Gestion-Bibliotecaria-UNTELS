package pe.edu.untels.dtos;

public class StudentRegisterDTO {
    private String codigoStudent;
    private String nameStudent;
    private String emailStudent;
    private String phoneStudent;
    private String carreraStudent;
    private String usernameUser;
    private String passwordUser;

    public StudentRegisterDTO() {}

    public String getCodigoStudent() { return codigoStudent; }
    public void setCodigoStudent(String codigoStudent) { this.codigoStudent = codigoStudent; }
    public String getNameStudent() { return nameStudent; }
    public void setNameStudent(String nameStudent) { this.nameStudent = nameStudent; }
    public String getEmailStudent() { return emailStudent; }
    public void setEmailStudent(String emailStudent) { this.emailStudent = emailStudent; }
    public String getPhoneStudent() { return phoneStudent; }
    public void setPhoneStudent(String phoneStudent) { this.phoneStudent = phoneStudent; }
    public String getCarreraStudent() { return carreraStudent; }
    public void setCarreraStudent(String carreraStudent) { this.carreraStudent = carreraStudent; }
    public String getUsernameUser() { return usernameUser; }
    public void setUsernameUser(String usernameUser) { this.usernameUser = usernameUser; }
    public String getPasswordUser() { return passwordUser; }
    public void setPasswordUser(String passwordUser) { this.passwordUser = passwordUser; }
}

