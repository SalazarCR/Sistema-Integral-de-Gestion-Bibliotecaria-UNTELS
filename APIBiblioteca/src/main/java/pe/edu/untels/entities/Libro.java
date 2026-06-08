package pe.edu.untels.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entidad Libro - Representa un libro en el catálogo de la biblioteca.
 * Proporciona información bibliográfica y de inventario.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "libros", indexes = {
    @Index(name = "idx_isbn", columnList = "isbn", unique = true),
    @Index(name = "idx_titulo", columnList = "titulo"),
    @Index(name = "idx_categoria", columnList = "categoria")
})
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Integer idLibro;

    @NotBlank(message = "El ISBN no puede estar vacío")
    @Column(name = "isbn", nullable = false, unique = true, length = 13)
    private String isbn;

    @NotBlank(message = "El título no puede estar vacío")
    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @NotBlank(message = "El autor no puede estar vacío")
    @Column(name = "autor", nullable = false, length = 200)
    private String autor;

    @Column(name = "editorial", length = 200)
    private String editorial;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "ano_publicacion")
    private Integer anoPublicacion;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "url_portada")
    private String urlPortada;

    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "stock", nullable = false)
    private Integer stock = 0;

    @Min(value = 0, message = "El stock total no puede ser negativo")
    @Column(name = "stock_total", nullable = false)
    private Integer stockTotal = 0;

    @Column(name = "paginas")
    private Integer paginas;

    @Column(name = "idioma", length = 50)
    private String idioma;

    @Column(name = "status_libro", nullable = false)
    private Boolean statusLibro = true;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    @Column(name = "creado_por", length = 100)
    private String creadoPor;

    @Column(name = "actualizado_por", length = 100)
    private String actualizadoPor;

    @Override
    public String toString() {
        return "Libro{" +
                "idLibro=" + idLibro +
                ", isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}

