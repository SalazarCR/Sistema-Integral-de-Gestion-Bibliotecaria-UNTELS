package pe.edu.untels.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"User\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;

    @Column(name = "usernameUser", length = 50, nullable = false, unique = true)
    private String usernameUser;

    @Column(name = "passwordUser", length = 255, nullable = false)
    private String passwordUser;

    @Column(name = "emailUser", length = 100, nullable = false, unique = true)
    private String emailUser;

    @Column(name = "statusUser", nullable = false)
    private boolean statusUser;

    @Column(name = "dateRegisterUser", nullable = false)
    private LocalDateTime dateRegisterUser;

    @ManyToOne
    @JoinColumn(name = "idRole", nullable = false)
    private Role role;

    public User() {}

    public User(String usernameUser, String passwordUser, String emailUser, boolean statusUser, LocalDateTime dateRegisterUser, Role role) {
        this.usernameUser = usernameUser;
        this.passwordUser = passwordUser;
        this.emailUser = emailUser;
        this.statusUser = statusUser;
        this.dateRegisterUser = dateRegisterUser;
        this.role = role;
    }

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
    public String getUsernameUser() { return usernameUser; }
    public void setUsernameUser(String usernameUser) { this.usernameUser = usernameUser; }
    public String getPasswordUser() { return passwordUser; }
    public void setPasswordUser(String passwordUser) { this.passwordUser = passwordUser; }
    public String getEmailUser() { return emailUser; }
    public void setEmailUser(String emailUser) { this.emailUser = emailUser; }
    public boolean isStatusUser() { return statusUser; }
    public void setStatusUser(boolean statusUser) { this.statusUser = statusUser; }
    public LocalDateTime getDateRegisterUser() { return dateRegisterUser; }
    public void setDateRegisterUser(LocalDateTime dateRegisterUser) { this.dateRegisterUser = dateRegisterUser; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}

