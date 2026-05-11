package pe.edu.untels.entities;

import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "Libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLibro;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "autor", length = 100, nullable = false)
    private String autor;

    @Column(name = "isbn", length = 20, unique = true)
    private String isbn;

    @Column(name = "stock")
    private int stock;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;
    public Libro() {
    }

    public Libro(String titulo, String autor, String isbn, int stock, String descripcion) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.stock = stock;
        this.descripcion = descripcion;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}