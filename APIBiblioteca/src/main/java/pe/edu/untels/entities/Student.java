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

    @Column(name = "carreraStudent", length = 100, nullable = false)
    private String carreraStudent;

    @Column(name = "statusStudent", nullable = false)
    private boolean statusStudent;

    @Column(name = "dateRegisterStudent", nullable = false)
    private LocalDate dateRegisterStudent;

    @OneToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    public Student() {}

    public Student(String codigoStudent, String nameStudent, String emailStudent, String phoneStudent, String carreraStudent, boolean statusStudent, LocalDate dateRegisterStudent, User user) {
        this.codigoStudent = codigoStudent;
        this.nameStudent = nameStudent;
        this.emailStudent = emailStudent;
        this.phoneStudent = phoneStudent;
        this.carreraStudent = carreraStudent;
        this.statusStudent = statusStudent;
        this.dateRegisterStudent = dateRegisterStudent;
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
    public String getCarreraStudent() { return carreraStudent; }
    public void setCarreraStudent(String carreraStudent) { this.carreraStudent = carreraStudent; }
    public boolean isStatusStudent() { return statusStudent; }
    public void setStatusStudent(boolean statusStudent) { this.statusStudent = statusStudent; }
    public LocalDate getDateRegisterStudent() { return dateRegisterStudent; }
    public void setDateRegisterStudent(LocalDate dateRegisterStudent) { this.dateRegisterStudent = dateRegisterStudent; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}

