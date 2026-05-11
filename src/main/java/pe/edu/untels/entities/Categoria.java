package pe.edu.untels.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCategoria;

    @Column(name = "nombreCategoria", length = 100, nullable = false)
    private String nombreCategoria;

    @Column(name = "descripcionCategoria", length = 300)
    private String descripcionCategoria;

    @OneToMany(mappedBy = "categoria")
    private List<Libro> libros;

    public Categoria() {
    }

    public Categoria(String nombreCategoria, String descripcionCategoria) {
        this.nombreCategoria = nombreCategoria;
        this.descripcionCategoria = descripcionCategoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}