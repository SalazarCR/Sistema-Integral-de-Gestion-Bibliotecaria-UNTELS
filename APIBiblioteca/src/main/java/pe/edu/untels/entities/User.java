package pe.edu.untels.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "username_user", unique = true, nullable = false, length = 100)
    private String usernameUser;

    @Column(name = "password_user", nullable = false, length = 255)
    private String passwordUser;

    @Column(name = "email_user", unique = true, length = 100)
    private String emailUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role", nullable = false)
    private Role role;

    @Column(name = "status_user", nullable = false)
    private Boolean statusUser = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public User() {}

    public User(String usernameUser, String passwordUser, String emailUser, Role role) {
        this.usernameUser = usernameUser;
        this.passwordUser = passwordUser;
        this.emailUser = emailUser;
        this.role = role;
        this.statusUser = true;
    }

    public Integer getIdUser() { return idUser; }
    public void setIdUser(Integer idUser) { this.idUser = idUser; }

    public String getUsernameUser() { return usernameUser; }
    public void setUsernameUser(String usernameUser) { this.usernameUser = usernameUser; }

    public String getPasswordUser() { return passwordUser; }
    public void setPasswordUser(String passwordUser) { this.passwordUser = passwordUser; }

    public String getEmailUser() { return emailUser; }
    public void setEmailUser(String emailUser) { this.emailUser = emailUser; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Boolean getStatusUser() { return statusUser; }
    public void setStatusUser(Boolean statusUser) { this.statusUser = statusUser; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "User{" + "idUser=" + idUser + ", usernameUser='" + usernameUser + '\'' + '}';
    }
}

