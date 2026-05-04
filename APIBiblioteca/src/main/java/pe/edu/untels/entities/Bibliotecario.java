package pe.edu.untels.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "bibliotecario")
public class Bibliotecario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String correo;
    private Boolean activo = true;

    public Bibliotecario() {}

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public Boolean getActivo() { return activo; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
