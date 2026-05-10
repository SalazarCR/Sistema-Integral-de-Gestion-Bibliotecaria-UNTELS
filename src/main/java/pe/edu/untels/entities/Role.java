package pe.edu.untels.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRole;

    @Column(name = "nameRole", length = 50, nullable = false, unique = true)
    private String nameRole;

    @Column(name = "descriptionRole", length = 200)
    private String descriptionRole;

    public Role() {}

    public Role(String nameRole, String descriptionRole) {
        this.nameRole = nameRole;
        this.descriptionRole = descriptionRole;
    }

    public int getIdRole() { return idRole; }
    public void setIdRole(int idRole) { this.idRole = idRole; }

    public String getNameRole() { return nameRole; }
    public void setNameRole(String nameRole) { this.nameRole = nameRole; }

    public String getDescriptionRole() { return descriptionRole; }
    public void setDescriptionRole(String descriptionRole) { this.descriptionRole = descriptionRole; }
}
