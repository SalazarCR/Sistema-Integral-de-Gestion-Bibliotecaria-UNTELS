package pe.edu.untels.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
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

    private PrestamoDTO mapearDto(Prestamo prestamo) {
        ModelMapper mapper = new ModelMapper();
        PrestamoDTO dto = mapper.map(prestamo, PrestamoDTO.class);
        if (prestamo.getLibro() != null) {
            dto.setTituloLibro(prestamo.getLibro().getTitulo());
        }
        if (prestamo.getEstudiante() != null) {
            dto.setNombreEstudiante(prestamo.getEstudiante().getNombre());
        }
        return dto;
    }

    @GetMapping("/lista")
    public ResponseEntity<List<PrestamoDTO>> listar() {
        List<PrestamoDTO> lista = prestamoService.list()
                .stream()
                .map(this::mapearDto)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Optional<Prestamo> prestamo = prestamoService.listId(id);

        if (prestamo.isPresent()) {
            return ResponseEntity.ok(mapearDto(prestamo.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Prestamo no encontrado");
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PrestamoDTO>> buscarPorEstado(@PathVariable String estado) {
        List<PrestamoDTO> lista = prestamoService.buscarPorEstado(estado)
                .stream()
                .map(this::mapearDto)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/estudiante/{idEstudiante}")
    public ResponseEntity<List<PrestamoDTO>> buscarPorEstudiante(@PathVariable int idEstudiante) {
        List<PrestamoDTO> lista = prestamoService.buscarPorEstudiante(idEstudiante)
                .stream()
                .map(this::mapearDto)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PostMapping("/solicitar")
    public ResponseEntity<?> solicitar(@RequestBody PrestamoDTO dto) {
        Optional<Usuario> estudianteOpt = usuarioService.listId(dto.getIdEstudiante());
        if (estudianteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Estudiante no encontrado");
        }

        Usuario estudiante = estudianteOpt.get();

        if (!"ACTIVO".equalsIgnoreCase(estudiante.getEstado())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("El estudiante no esta activo y no puede solicitar prestamos");
        }

        List<Sancion> sancionesActivas = sancionService.buscarPorEstudiante(dto.getIdEstudiante())
                .stream()
                .filter(s -> s.getEstado().equals("activa"))
                .toList();

        if (!sancionesActivas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("El estudiante tiene una sancion activa y no puede solicitar prestamos");
        }

        boolean tienePrestamosVencidos = prestamoService.buscarPorEstudiante(dto.getIdEstudiante())
                .stream()
                .anyMatch(p -> p.getEstado().equals("vencido"));

        if (tienePrestamosVencidos) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El estudiante tiene prestamos vencidos pendientes de devolucion");
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

        // fechaEntrega es NOT NULL en BD: se calcula en el servidor a partir de la
        // configuracion (diasMaxPrestamo) en lugar de confiar en que el frontend la envie.
        int diasMaxPrestamo = configOpt.map(ConfiguracionBiblioteca::getDiasMaxPrestamo).orElse(15);

        Prestamo prestamo = new Prestamo();
        prestamo.setLibro(libroOpt.get());
        prestamo.setEstudiante(estudiante);
        prestamo.setFecha(LocalDateTime.now());
        prestamo.setFechaEntrega(LocalDateTime.now().plusDays(diasMaxPrestamo));
        prestamo.setEstado("solicitado");
        prestamo.setMotivo(dto.getMotivo());
        prestamo.setCurso(dto.getCurso());
        prestamo.setObservaciones(dto.getObservaciones());

        Prestamo guardado = prestamoService.insert(prestamo);

        notificarNuevaSolicitudABibliotecarios(guardado);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapearDto(guardado));
    }

    private void notificarNuevaSolicitudABibliotecarios(Prestamo prestamo) {
        List<Usuario> bibliotecarios = usuarioService.buscarPorRol("BIBLIOTECARIO")
                .stream()
                .filter(u -> "ACTIVO".equalsIgnoreCase(u.getEstado()))
                .toList();

        for (Usuario bibliotecario : bibliotecarios) {
            Notificacion notificacion = new Notificacion();
            notificacion.setEstudiante(bibliotecario);
            notificacion.setTipo("nueva_solicitud");
            notificacion.setMensaje("Nueva solicitud de prestamo: '" + prestamo.getLibro().getTitulo()
                    + "' pedida por " + prestamo.getEstudiante().getNombre() + ". Revisala en Prestamos.");
            notificacion.setFecha(LocalDateTime.now());
            notificacion.setLeida(false);
            notificacionService.insert(notificacion);
        }
    }

    @PutMapping("/aprobar/{idPrestamo}")
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

        boolean esVirtual = libro.getRecurso() != null && !libro.getRecurso().isBlank();
        String mensajeAprobacion = esVirtual
                ? "Tu prestamo del libro '" + libro.getTitulo() + "' fue confirmado. Ya puedes acceder al recurso virtual desde el catalogo."
                : "Tu prestamo del libro '" + libro.getTitulo() + "' fue confirmado. Ya puedes recogerlo en biblioteca.";

        Notificacion notificacion = new Notificacion();
        notificacion.setEstudiante(prestamo.getEstudiante());
        notificacion.setTipo("confirmacion");
        notificacion.setMensaje(mensajeAprobacion);
        notificacion.setFecha(LocalDateTime.now());
        notificacion.setLeida(false);
        notificacionService.insert(notificacion);

        return ResponseEntity.ok("Prestamo aprobado correctamente");
    }

    @PutMapping("/rechazar/{idPrestamo}")
    public ResponseEntity<String> rechazar(@PathVariable int idPrestamo, @RequestBody(required = false) Map<String, String> body) {
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

        String motivo = body != null ? body.get("motivo") : null;

        prestamo.setEstado("rechazado");
        prestamo.setObservaciones(motivo);
        prestamoService.edit(prestamo);

        String mensajeRechazo = "Tu solicitud de prestamo del libro '" + prestamo.getLibro().getTitulo() + "' ha sido rechazada"
                + (motivo != null && !motivo.isBlank() ? ". Motivo: " + motivo : "");

        Notificacion notificacion = new Notificacion();
        notificacion.setEstudiante(prestamo.getEstudiante());
        notificacion.setTipo("rechazo");
        notificacion.setMensaje(mensajeRechazo);
        notificacion.setFecha(LocalDateTime.now());
        notificacion.setLeida(false);
        notificacionService.insert(notificacion);

        return ResponseEntity.ok("Prestamo rechazado correctamente");
    }

    @PutMapping("/devolver")
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
