package pe.edu.untels.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Carrera")
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCarrera;

    @Column(name = "nameCarrera", length = 100, nullable = false, unique = true)
    private String nameCarrera;

    @Column(name = "descriptionCarrera", length = 300)
    private String descriptionCarrera;

    @Column(name = "statusCarrera", nullable = false)
    private boolean statusCarrera;

    @OneToMany(mappedBy = "carrera")
    private List<Student> students;

    public Carrera() {}

    public Carrera(String nameCarrera, String descriptionCarrera, boolean statusCarrera) {
        this.nameCarrera = nameCarrera;
        this.descriptionCarrera = descriptionCarrera;
        this.statusCarrera = statusCarrera;
    }

    public int getIdCarrera() { return idCarrera; }
    public void setIdCarrera(int idCarrera) { this.idCarrera = idCarrera; }
    public String getNameCarrera() { return nameCarrera; }
    public void setNameCarrera(String nameCarrera) { this.nameCarrera = nameCarrera; }
    public String getDescriptionCarrera() { return descriptionCarrera; }
    public void setDescriptionCarrera(String descriptionCarrera) { this.descriptionCarrera = descriptionCarrera; }
    public boolean isStatusCarrera() { return statusCarrera; }
    public void setStatusCarrera(boolean statusCarrera) { this.statusCarrera = statusCarrera; }
    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
}

