package pe.edu.untels.dtos;

public class StudentDTO {
    private int idStudent;
    private String codigoStudent;
    private String nameStudent;
    private String emailStudent;
    private String phoneStudent;
    private String carreraName;
    private boolean statusStudent;
    private boolean libraryAccessStudent;

    public StudentDTO() {}

    public int getIdStudent() { return idStudent; }
    public void setIdStudent(int idStudent) { this.idStudent = idStudent; }
    public String getCodigoStudent() { return codigoStudent; }
    public void setCodigoStudent(String codigoStudent) { this.codigoStudent = codigoStudent; }
    public String getNameStudent() { return nameStudent; }
    public void setNameStudent(String nameStudent) { this.nameStudent = nameStudent; }
    public String getEmailStudent() { return emailStudent; }
    public void setEmailStudent(String emailStudent) { this.emailStudent = emailStudent; }
    public String getPhoneStudent() { return phoneStudent; }
    public void setPhoneStudent(String phoneStudent) { this.phoneStudent = phoneStudent; }
    public String getCarreraName() { return carreraName; }
    public void setCarreraName(String carreraName) { this.carreraName = carreraName; }
    public boolean isStatusStudent() { return statusStudent; }
    public void setStatusStudent(boolean statusStudent) { this.statusStudent = statusStudent; }
    public boolean isLibraryAccessStudent() { return libraryAccessStudent; }
    public void setLibraryAccessStudent(boolean libraryAccessStudent) { this.libraryAccessStudent = libraryAccessStudent; }
}

