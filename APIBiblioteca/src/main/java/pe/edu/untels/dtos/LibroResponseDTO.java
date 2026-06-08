package pe.edu.untels.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para respuestas de consultas de libros.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LibroResponseDTO {

    private Integer idLibro;
    private String isbn;
    private String titulo;
    private String autor;
    private String editorial;
    private String categoria;
    private Integer anoPublicacion;
    private String descripcion;
    private String urlPortada;
    private Integer stock;
    private Integer stockTotal;
    private Integer paginas;
    private String idioma;
    private Boolean statusLibro;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private String creadoPor;
    private String actualizadoPor;
    private Boolean disponible;
}

