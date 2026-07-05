package pe.edu.untels.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.untels.dtos.PrestamoDTO;
import pe.edu.untels.entities.ConfiguracionBiblioteca;
import pe.edu.untels.entities.Libro;
import pe.edu.untels.entities.Notificacion;
import pe.edu.untels.entities.Prestamo;
import pe.edu.untels.entities.Sancion;
import pe.edu.untels.entities.Usuario;
import pe.edu.untels.servicesinterfaces.IConfiguracionBibliotecaService;
import pe.edu.untels.servicesinterfaces.ILibroService;
import pe.edu.untels.servicesinterfaces.INotificacionService;
import pe.edu.untels.servicesinterfaces.IPrestamoService;
import pe.edu.untels.servicesinterfaces.ISancionService;
import pe.edu.untels.servicesinterfaces.IUsuarioService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private IPrestamoService prestamoService;

    @Autowired
    private ILibroService libroService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IConfiguracionBibliotecaService configuracionService;

    @Autowired
    private ISancionService sancionService;

    @Autowired
    private INotificacionService notificacionService;

    @GetMapping("/lista")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<List<PrestamoDTO>> listar() {
        ModelMapper mapper = new ModelMapper();

        List<PrestamoDTO> lista = prestamoService.list()
                .stream()
                .map(prestamo -> mapper.map(prestamo, PrestamoDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        ModelMapper mapper = new ModelMapper();
        Optional<Prestamo> prestamo = prestamoService.listId(id);

        if (prestamo.isPresent()) {
            PrestamoDTO dto = mapper.map(prestamo.get(), PrestamoDTO.class);
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Prestamo no encontrado");
    }

    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<List<PrestamoDTO>> buscarPorEstado(@PathVariable String estado) {
        ModelMapper mapper = new ModelMapper();

        List<PrestamoDTO> lista = prestamoService.buscarPorEstado(estado)
                .stream()
                .map(prestamo -> mapper.map(prestamo, PrestamoDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/estudiante/{idEstudiante}")
    public ResponseEntity<List<PrestamoDTO>> buscarPorEstudiante(@PathVariable int idEstudiante) {
        ModelMapper mapper = new ModelMapper();

        List<PrestamoDTO> lista = prestamoService.buscarPorEstudiante(idEstudiante)
                .stream()
                .map(prestamo -> mapper.map(prestamo, PrestamoDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PostMapping("/solicitar")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<?> solicitar(@Valid @RequestBody PrestamoDTO dto) {
        ModelMapper mapper = new ModelMapper();

        Optional<Usuario> estudianteOpt = usuarioService.listId(dto.getIdEstudiante());
        if (estudianteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Estudiante no encontrado");
        }

        Usuario estudiante = estudianteOpt.get();

        List<Sancion> sancionesActivas = sancionService.buscarPorEstudiante(dto.getIdEstudiante())
                .stream()
                .filter(s -> s.getEstado().equals("activa"))
                .toList();

        if (!sancionesActivas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("El estudiante tiene una sancion activa y no puede solicitar prestamos");
        }

        Optional<Libro> libroOpt = libroService.listId(dto.getIdLibro());
        if (libroOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Libro no encontrado");
        }

        if (libroOpt.get().getStock() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El libro no tiene stock disponible");
        }

        Optional<ConfiguracionBiblioteca> configOpt = configuracionService.obtener();
        if (configOpt.isPresent()) {
            long prestamosActivos = prestamoService.buscarPorEstudiante(dto.getIdEstudiante())
                    .stream()
                    .filter(p -> p.getEstado().equals("solicitado") || p.getEstado().equals("vigente"))
                    .count();

            if (prestamosActivos >= configOpt.get().getLimitePrestamos()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El estudiante ha superado el limite de prestamos permitidos");
            }
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setLibro(libroOpt.get());
        prestamo.setEstudiante(estudiante);
        prestamo.setFecha(LocalDateTime.now());
        prestamo.setFechaEntrega(dto.getFechaEntrega());
        prestamo.setEstado("solicitado");
        prestamo.setMotivo(dto.getMotivo());
        prestamo.setCurso(dto.getCurso());
        prestamo.setObservaciones(dto.getObservaciones());

        Prestamo guardado = prestamoService.insert(prestamo);
        PrestamoDTO responseDTO = mapper.map(guardado, PrestamoDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/aprobar/{idPrestamo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<String> aprobar(@PathVariable int idPrestamo) {
        Optional<Prestamo> prestamoOpt = prestamoService.listId(idPrestamo);

        if (prestamoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Prestamo no encontrado");
        }

        Prestamo prestamo = prestamoOpt.get();

        if (!prestamo.getEstado().equals("solicitado")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El prestamo no esta en estado solicitado");
        }

        Libro libro = prestamo.getLibro();
        if (libro.getStock() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El libro no tiene stock disponible");
        }

        libro.setStock(libro.getStock() - 1);
        libroService.edit(libro);

        prestamo.setEstado("vigente");
        prestamo.setFechaRecojo(LocalDateTime.now());
        prestamoService.edit(prestamo);

        Notificacion notificacion = new Notificacion();
        notificacion.setEstudiante(prestamo.getEstudiante());
        notificacion.setTipo("confirmacion");
        notificacion.setMensaje("Tu prestamo del libro '" + libro.getTitulo() + "' ha sido aprobado");
        notificacion.setFecha(LocalDateTime.now());
        notificacion.setLeida(false);
        notificacionService.insert(notificacion);

        return ResponseEntity.ok("Prestamo aprobado correctamente");
    }

    @PutMapping("/rechazar/{idPrestamo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<String> rechazar(@PathVariable int idPrestamo,
                                            @RequestParam(required = false) String motivo) {
        Optional<Prestamo> prestamoOpt = prestamoService.listId(idPrestamo);

        if (prestamoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Prestamo no encontrado");
        }

        Prestamo prestamo = prestamoOpt.get();

        if (!prestamo.getEstado().equals("solicitado")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El prestamo no esta en estado solicitado");
        }

        prestamo.setEstado("rechazado");
        prestamoService.edit(prestamo);

        String mensaje = "Tu solicitud de prestamo del libro '" + prestamo.getLibro().getTitulo() + "' ha sido rechazada";
        if (motivo != null && !motivo.isBlank()) {
            mensaje += ": " + motivo;
        }

        Notificacion notificacion = new Notificacion();
        notificacion.setEstudiante(prestamo.getEstudiante());
        notificacion.setTipo("rechazo");
        notificacion.setMensaje(mensaje);
        notificacion.setFecha(LocalDateTime.now());
        notificacion.setLeida(false);
        notificacionService.insert(notificacion);

        return ResponseEntity.ok("Prestamo rechazado correctamente");
    }

    @PutMapping("/devolver")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<?> devolver(@RequestBody PrestamoDTO dto) {
        Optional<Prestamo> prestamoOpt = prestamoService.listId(dto.getIdPrestamo());

        if (prestamoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Prestamo no encontrado");
        }

        Prestamo prestamo = prestamoOpt.get();

        if (!prestamo.getEstado().equals("vigente") && !prestamo.getEstado().equals("vencido")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El prestamo no esta en estado vigente o vencido");
        }

        Libro libro = prestamo.getLibro();
        if (libro.getStock() < libro.getStockTotal()) {
            libro.setStock(libro.getStock() + 1);
            libroService.edit(libro);
        }

        prestamo.setEstado("devuelto");
        prestamo.setFechaDevolucion(LocalDateTime.now());
        prestamo.setEstadoDevolucion(dto.getEstadoDevolucion());
        prestamo.setObservacionesDev(dto.getObservacionesDev());
        prestamoService.edit(prestamo);

        if (prestamo.getFechaEntrega() != null) {
            long diasRetraso = ChronoUnit.DAYS.between(prestamo.getFechaEntrega(), LocalDateTime.now());

            if (diasRetraso > 0) {
                Optional<ConfiguracionBiblioteca> configOpt = configuracionService.obtener();
                double multaPorDia = configOpt.map(ConfiguracionBiblioteca::getMultaPorDia).orElse(1.0);

                Sancion sancion = new Sancion();
                sancion.setEstudiante(prestamo.getEstudiante());
                sancion.setMotivo("Devolucion tardia de libro: " + libro.getTitulo() + " (" + diasRetraso + " dias de retraso)");
                sancion.setDiasSuspension((int) diasRetraso);
                sancion.setMulta(diasRetraso * multaPorDia);
                sancion.setEstado("activa");
                sancion.setFechaCreacion(LocalDateTime.now());
                sancion.setFechaFin(LocalDateTime.now().plusDays(diasRetraso));
                sancionService.insert(sancion);

                Notificacion notificacion = new Notificacion();
                notificacion.setEstudiante(prestamo.getEstudiante());
                notificacion.setTipo("sancion");
                notificacion.setMensaje("Se ha generado una sancion por devolucion tardia. Dias de suspension: " + diasRetraso + ", Multa: S/" + String.format("%.2f", diasRetraso * multaPorDia));
                notificacion.setFecha(LocalDateTime.now());
                notificacion.setLeida(false);
                notificacionService.insert(notificacion);
            }
        }

        return ResponseEntity.ok("Libro devuelto correctamente");
    }
}
