package pe.edu.untels.dtos;

public class LibroEnriquecidoDTO {

    private String titulo;
    private String autor;
    private String isbn;
    private String descripcion;
    private String portadaUrl;
    private String previewUrl;
    private String audioLibroUrl;

    public LibroEnriquecidoDTO() {
    }

    public LibroEnriquecidoDTO(String titulo, String autor,
                               String isbn, String descripcion,
                               String portadaUrl, String previewUrl,
                               String audioLibroUrl) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.descripcion = descripcion;
        this.portadaUrl = portadaUrl;
        this.previewUrl = previewUrl;
        this.audioLibroUrl = audioLibroUrl;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPortadaUrl() {
        return portadaUrl;
    }

    public void setPortadaUrl(String portadaUrl) {
        this.portadaUrl = portadaUrl;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getAudioLibroUrl() {
        return audioLibroUrl;
    }

    public void setAudioLibroUrl(String audioLibroUrl) {
        this.audioLibroUrl = audioLibroUrl;
    }
}