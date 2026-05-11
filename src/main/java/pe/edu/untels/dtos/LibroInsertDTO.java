package pe.edu.untels.dtos;

public class LibroInsertDTO {

    private String titulo;
    private String autor;
    private String isbn;
    private int stock;
    private String descripcion;

    public LibroInsertDTO() {
    }

    public LibroInsertDTO(String titulo, String autor,
                          String isbn, int stock, String descripcion) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.stock = stock;
        this.descripcion = descripcion;
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