package pe.edu.untels.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStudent;

    @Column(name = "codigoStudent", length = 20, nullable = false, unique = true)
    private String codigoStudent;

    @Column(name = "nameStudent", length = 100, nullable = false)
    private String nameStudent;

    @Column(name = "emailStudent", length = 100, nullable = false)
    private String emailStudent;

    @Column(name = "phoneStudent", length = 20)
    private String phoneStudent;

    @Column(name = "statusStudent", nullable = false)
    private boolean statusStudent;

    @Column(name = "libraryAccessStudent", nullable = false)
    private boolean libraryAccessStudent;

    @Column(name = "dateRegisterStudent", nullable = false)
    private LocalDate dateRegisterStudent;

    @ManyToOne
    @JoinColumn(name = "idCarrera", nullable = false)
    private Carrera carrera;

    @OneToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    public Student() {}

    public Student(String codigoStudent, String nameStudent, String emailStudent, String phoneStudent, boolean statusStudent, LocalDate dateRegisterStudent, Carrera carrera, User user) {
        this.codigoStudent = codigoStudent;
        this.nameStudent = nameStudent;
        this.emailStudent = emailStudent;
        this.phoneStudent = phoneStudent;
        this.statusStudent = statusStudent;
        this.libraryAccessStudent = true;
        this.dateRegisterStudent = dateRegisterStudent;
        this.carrera = carrera;
        this.user = user;
    }

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
    public boolean isStatusStudent() { return statusStudent; }
    public void setStatusStudent(boolean statusStudent) { this.statusStudent = statusStudent; }
    public boolean isLibraryAccessStudent() { return libraryAccessStudent; }
    public void setLibraryAccessStudent(boolean libraryAccessStudent) { this.libraryAccessStudent = libraryAccessStudent; }
    public LocalDate getDateRegisterStudent() { return dateRegisterStudent; }
    public void setDateRegisterStudent(LocalDate dateRegisterStudent) { this.dateRegisterStudent = dateRegisterStudent; }
    public Carrera getCarrera() { return carrera; }
    public void setCarrera(Carrera carrera) { this.carrera = carrera; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}

