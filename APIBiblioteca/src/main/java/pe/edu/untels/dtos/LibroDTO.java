package pe.edu.untels.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para solicitudes de creación y actualización de libros.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibroDTO {

    @NotBlank(message = "El ISBN no puede estar vacío")
    @Size(min = 10, max = 13, message = "El ISBN debe tener entre 10 y 13 caracteres")
    private String isbn;

    @NotBlank(message = "El título no puede estar vacío")
    @Size(min = 3, max = 255, message = "El título debe tener entre 3 y 255 caracteres")
    private String titulo;

    @NotBlank(message = "El autor no puede estar vacío")
    @Size(min = 3, max = 200, message = "El autor debe tener entre 3 y 200 caracteres")
    private String autor;

    @Size(max = 200, message = "La editorial no puede exceder 200 caracteres")
    private String editorial;

    @Size(max = 100, message = "La categoría no puede exceder 100 caracteres")
    private String categoria;

    @Min(value = 1900, message = "El año de publicación debe ser válido")
    @Max(value = 2100, message = "El año de publicación no puede ser mayor a 2100")
    private Integer anoPublicacion;

    private String descripcion;

    private String urlPortada;

    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "El stock total no puede ser nulo")
    @Min(value = 0, message = "El stock total no puede ser negativo")
    private Integer stockTotal;

    @Min(value = 1, message = "El número de páginas debe ser válido")
    private Integer paginas;

    @Size(max = 50, message = "El idioma no puede exceder 50 caracteres")
    private String idioma;
}


