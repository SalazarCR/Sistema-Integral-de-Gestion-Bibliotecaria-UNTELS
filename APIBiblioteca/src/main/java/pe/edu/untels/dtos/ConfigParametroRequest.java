package pe.edu.untels.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.untels.enums.TipoParametro;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigParametroRequest {

    @NotBlank(message = "El nombre del parámetro no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El valor no puede estar vacío")
    @Size(min = 1, max = 500, message = "El valor no puede exceder 500 caracteres")
    private String valor;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    @NotNull(message = "El tipo de parámetro no puede ser nulo")
    private TipoParametro tipo;
}

