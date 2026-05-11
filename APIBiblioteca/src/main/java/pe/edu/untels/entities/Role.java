package pe.edu.untels.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Integer idRole;

    @Column(name = "name_role", unique = true, nullable = false, length = 50)
    private String nameRole;

    @Column(name = "status_role", nullable = false)
    private Boolean statusRole = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Role() {}

    public Role(String nameRole) {
        this.nameRole = nameRole;
        this.statusRole = true;
    }

    public Integer getIdRole() { return idRole; }
    public void setIdRole(Integer idRole) { this.idRole = idRole; }

    public String getNameRole() { return nameRole; }
    public void setNameRole(String nameRole) { this.nameRole = nameRole; }

    public Boolean getStatusRole() { return statusRole; }
    public void setStatusRole(Boolean statusRole) { this.statusRole = statusRole; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Role{" + "idRole=" + idRole + ", nameRole='" + nameRole + '\'' + '}';
    }
}

