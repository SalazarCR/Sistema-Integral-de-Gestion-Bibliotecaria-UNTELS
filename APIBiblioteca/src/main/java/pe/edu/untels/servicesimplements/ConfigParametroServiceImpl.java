package pe.edu.untels.servicesimplements;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.untels.dtos.ConfigParametroRequest;
import pe.edu.untels.dtos.ConfigParametroResponse;
import pe.edu.untels.entities.ConfigParametro;
import pe.edu.untels.enums.TipoParametro;
import pe.edu.untels.exceptions.ParametroValidationException;
import pe.edu.untels.repositories.ConfigParametroRepository;
import pe.edu.untels.servicesinterfaces.IConfigParametroService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ConfigParametroServiceImpl implements IConfigParametroService {

    private final ConfigParametroRepository configParametroRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ConfigParametroResponse> obtenerTodos() {
        log.info("Obteniendo todos los parámetros de configuración");
        return configParametroRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ConfigParametroResponse obtenerPorNombre(String nombre) {
        log.info("Obteniendo parámetro de configuración: {}", nombre);
        return configParametroRepository.findByNombre(nombre)
                .map(this::convertToResponse)
                .orElseThrow(() -> new ParametroValidationException(
                        "Parámetro de configuración no encontrado: " + nombre));
    }

    @Override
    public ConfigParametroResponse crear(ConfigParametroRequest request) {
        log.info("Creating new config parameter: {}", request.getNombre());

        // Validar que el parámetro no exista
        if (configParametroRepository.existsByNombre(request.getNombre())) {
            throw new ParametroValidationException(
                    "Parámetro con nombre '" + request.getNombre() + "' ya existe");
        }

        // Validar el valor según el tipo
        validarValorPorTipo(request.getValor(), request.getTipo());

        // Crear la entidad
        ConfigParametro configParametro = ConfigParametro.builder()
                .nombre(request.getNombre())
                .valor(request.getValor())
                .descripcion(request.getDescripcion())
                .tipo(request.getTipo())
                .creadoPor("ADMIN") // Puede obtenerse del contexto de autenticación
                .actualizadoPor("ADMIN")
                .build();

        ConfigParametro saved = configParametroRepository.save(configParametro);
        log.info("Parámetro de configuración creado exitosamente: {}", saved.getId());

        return convertToResponse(saved);
    }

    @Override
    public ConfigParametroResponse actualizar(String nombre, ConfigParametroRequest request) {
        log.info("Actualizando parámetro de configuración: {}", nombre);

        ConfigParametro configParametro = configParametroRepository.findByNombre(nombre)
                .orElseThrow(() -> new ParametroValidationException(
                        "Parámetro no encontrado: " + nombre));

        // Validar el nuevo valor según el tipo
        validarValorPorTipo(request.getValor(), request.getTipo());

        // Actualizar campos
        configParametro.setValor(request.getValor());
        configParametro.setDescripcion(request.getDescripcion());
        configParametro.setTipo(request.getTipo());
        configParametro.setActualizadoPor("ADMIN");

        ConfigParametro updated = configParametroRepository.save(configParametro);
        log.info("Parámetro actualizado exitosamente: {}", updated.getId());

        return convertToResponse(updated);
    }

    @Override
    public void eliminar(String nombre) {
        log.info("Eliminando parámetro de configuración: {}", nombre);

        if (!configParametroRepository.existsByNombre(nombre)) {
            throw new ParametroValidationException(
                    "Parámetro no encontrado: " + nombre);
        }

        configParametroRepository.deleteByNombre(nombre);
        log.info("Parámetro eliminado exitosamente: {}", nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public int obtenerDiasPrestamo() {
        log.debug("Obteniendo días de préstamo");
        return configParametroRepository.findByTipo(TipoParametro.DIAS_PRESTAMO)
                .stream()
                .findFirst()
                .map(p -> Integer.parseInt(p.getValor()))
                .orElse(15); // Valor por defecto
    }

    @Override
    @Transactional(readOnly = true)
    public int obtenerLimitePrestamos() {
        log.debug("Obteniendo límite de préstamos");
        return configParametroRepository.findByTipo(TipoParametro.LIMITE_PRESTAMOS)
                .stream()
                .findFirst()
                .map(p -> Integer.parseInt(p.getValor()))
                .orElse(5); // Valor por defecto
    }

    @Override
    @Transactional(readOnly = true)
    public double obtenerMultaRetraso() {
        log.debug("Obteniendo multa por retraso");
        return configParametroRepository.findByTipo(TipoParametro.MULTA_RETRASO)
                .stream()
                .findFirst()
                .map(p -> Double.parseDouble(p.getValor()))
                .orElse(5.0); // Valor por defecto en soles
    }

    private void validarValorPorTipo(String valor, TipoParametro tipo) {
        int valorInt;
        try {
            valorInt = Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new ParametroValidationException(
                    "El valor debe ser numérico para el tipo: " + tipo);
        }

        if (!tipo.validarValor(valorInt)) {
            throw new ParametroValidationException(
                    String.format("Valor no válido para %s. Debe estar entre %d y %d",
                            tipo.getDescripcion(), tipo.getValorMinimo(), tipo.getValorMaximo()));
        }
    }

    private ConfigParametroResponse convertToResponse(ConfigParametro entity) {
        return ConfigParametroResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .valor(entity.getValor())
                .descripcion(entity.getDescripcion())
                .tipo(entity.getTipo())
                .fechaCreacion(entity.getFechaCreacion())
                .fechaActualizacion(entity.getFechaActualizacion())
                .creadoPor(entity.getCreadoPor())
                .actualizadoPor(entity.getActualizadoPor())
                .build();
    }
}

