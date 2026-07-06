package pe.edu.untels.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias de las reglas de negocio de prestamos.
 *
 * Estas reglas (sin sancion activa, stock > 0, limite de prestamos simultaneos,
 * baja/alza de stock, calculo de multa por retraso, notificaciones) estan
 * implementadas en PrestamoController y NO en PrestamoServiceImplement (que solo
 * hace CRUD). Por eso el controller se prueba aqui de forma unitaria, sin
 * @SpringBootTest ni MockMvc: se instancia con @InjectMocks y se le inyectan mocks
 * de todos los servicios que usa.
 */
@ExtendWith(MockitoExtension.class)
class PrestamoControllerTest {

    @Mock
    private IPrestamoService prestamoService;
    @Mock
    private ILibroService libroService;
    @Mock
    private IUsuarioService usuarioService;
    @Mock
    private IConfiguracionBibliotecaService configuracionService;
    @Mock
    private ISancionService sancionService;
    @Mock
    private INotificacionService notificacionService;

    @InjectMocks
    private PrestamoController prestamoController;

    private Usuario estudiante;
    private Libro libro;

    @BeforeEach
    void setUp() {
        estudiante = new Usuario();
        estudiante.setIdUsuario(1);
        estudiante.setNombre("Juan Perez");
        estudiante.setEstado("ACTIVO");

        libro = new Libro();
        libro.setIdLibro(10);
        libro.setTitulo("Clean Code");
        libro.setStock(2);
        libro.setStockTotal(3);
    }

    private PrestamoDTO dtoSolicitar() {
        PrestamoDTO dto = new PrestamoDTO();
        dto.setIdEstudiante(1);
        dto.setIdLibro(10);
        dto.setMotivo("Tarea");
        return dto;
    }

    // ---------- solicitar ----------

    @Test
    void solicitar_estudianteNoExiste_retorna404() {
        when(usuarioService.listId(1)).thenReturn(Optional.empty());

        ResponseEntity<?> respuesta = prestamoController.solicitar(dtoSolicitar());

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        verify(prestamoService, never()).insert(any());
    }

    @Test
    void solicitar_estudianteInactivo_retorna403() {
        estudiante.setEstado("INACTIVO");
        when(usuarioService.listId(1)).thenReturn(Optional.of(estudiante));

        ResponseEntity<?> respuesta = prestamoController.solicitar(dtoSolicitar());

        assertEquals(HttpStatus.FORBIDDEN, respuesta.getStatusCode());
        verify(prestamoService, never()).insert(any());
    }

    @Test
    void solicitar_conSancionActiva_retorna403() {
        when(usuarioService.listId(1)).thenReturn(Optional.of(estudiante));

        Sancion sancionActiva = new Sancion();
        sancionActiva.setEstado("activa");
        when(sancionService.buscarPorEstudiante(1)).thenReturn(List.of(sancionActiva));

        ResponseEntity<?> respuesta = prestamoController.solicitar(dtoSolicitar());

        assertEquals(HttpStatus.FORBIDDEN, respuesta.getStatusCode());
        verify(prestamoService, never()).insert(any());
    }

    @Test
    void solicitar_conPrestamoVencidoPendiente_retorna400() {
        when(usuarioService.listId(1)).thenReturn(Optional.of(estudiante));
        when(sancionService.buscarPorEstudiante(1)).thenReturn(List.of());

        Prestamo vencido = new Prestamo();
        vencido.setEstado("vencido");
        when(prestamoService.buscarPorEstudiante(1)).thenReturn(List.of(vencido));

        ResponseEntity<?> respuesta = prestamoController.solicitar(dtoSolicitar());

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        verify(prestamoService, never()).insert(any());
    }

    @Test
    void solicitar_libroNoExiste_retorna404() {
        when(usuarioService.listId(1)).thenReturn(Optional.of(estudiante));
        when(sancionService.buscarPorEstudiante(1)).thenReturn(List.of());
        when(prestamoService.buscarPorEstudiante(1)).thenReturn(List.of());
        when(libroService.listId(10)).thenReturn(Optional.empty());

        ResponseEntity<?> respuesta = prestamoController.solicitar(dtoSolicitar());

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        verify(prestamoService, never()).insert(any());
    }

    @Test
    void solicitar_libroSinStock_retorna400() {
        libro.setStock(0);
        when(usuarioService.listId(1)).thenReturn(Optional.of(estudiante));
        when(sancionService.buscarPorEstudiante(1)).thenReturn(List.of());
        when(prestamoService.buscarPorEstudiante(1)).thenReturn(List.of());
        when(libroService.listId(10)).thenReturn(Optional.of(libro));

        ResponseEntity<?> respuesta = prestamoController.solicitar(dtoSolicitar());

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        verify(prestamoService, never()).insert(any());
    }

    @Test
    void solicitar_limiteDePrestamosSuperado_retorna400() {
        when(usuarioService.listId(1)).thenReturn(Optional.of(estudiante));
        when(sancionService.buscarPorEstudiante(1)).thenReturn(List.of());
        when(libroService.listId(10)).thenReturn(Optional.of(libro));

        Prestamo vigente1 = new Prestamo();
        vigente1.setEstado("vigente");
        Prestamo vigente2 = new Prestamo();
        vigente2.setEstado("solicitado");
        when(prestamoService.buscarPorEstudiante(1)).thenReturn(List.of(vigente1, vigente2));

        ConfiguracionBiblioteca config = new ConfiguracionBiblioteca();
        config.setLimitePrestamos(2);
        config.setDiasMaxPrestamo(15);
        when(configuracionService.obtener()).thenReturn(Optional.of(config));

        ResponseEntity<?> respuesta = prestamoController.solicitar(dtoSolicitar());

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        verify(prestamoService, never()).insert(any());
    }

    @Test
    void solicitar_exitoso_creaPrestamoConFechaSegunConfigYNotificaBibliotecariosActivos() {
        when(usuarioService.listId(1)).thenReturn(Optional.of(estudiante));
        when(sancionService.buscarPorEstudiante(1)).thenReturn(List.of());
        when(libroService.listId(10)).thenReturn(Optional.of(libro));
        when(prestamoService.buscarPorEstudiante(1)).thenReturn(List.of());

        ConfiguracionBiblioteca config = new ConfiguracionBiblioteca();
        config.setLimitePrestamos(5);
        config.setDiasMaxPrestamo(7);
        when(configuracionService.obtener()).thenReturn(Optional.of(config));

        when(prestamoService.insert(any(Prestamo.class))).thenAnswer(inv -> inv.getArgument(0));

        Usuario bibliotecarioActivo = new Usuario();
        bibliotecarioActivo.setNombre("Biblio Activo");
        bibliotecarioActivo.setEstado("ACTIVO");
        Usuario bibliotecarioInactivo = new Usuario();
        bibliotecarioInactivo.setNombre("Biblio Inactivo");
        bibliotecarioInactivo.setEstado("INACTIVO");
        when(usuarioService.buscarPorRol("BIBLIOTECARIO"))
                .thenReturn(List.of(bibliotecarioActivo, bibliotecarioInactivo));

        LocalDateTime antes = LocalDateTime.now();
        ResponseEntity<?> respuesta = prestamoController.solicitar(dtoSolicitar());

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());

        ArgumentCaptor<Prestamo> captor = ArgumentCaptor.forClass(Prestamo.class);
        verify(prestamoService).insert(captor.capture());
        Prestamo guardado = captor.getValue();
        assertEquals("solicitado", guardado.getEstado());
        long diasHastaEntrega = ChronoUnit.DAYS.between(antes, guardado.getFechaEntrega());
        assertTrue(diasHastaEntrega >= 6 && diasHastaEntrega <= 7,
                "la fecha de entrega debe calcularse con diasMaxPrestamo de la configuracion");

        // Solo se notifica al bibliotecario ACTIVO
        verify(notificacionService, times(1)).insert(any(Notificacion.class));
    }

    @Test
    void solicitar_sinConfiguracion_usaQuinceDiasPorDefectoYOmiteValidacionDeLimite() {
        when(usuarioService.listId(1)).thenReturn(Optional.of(estudiante));
        when(sancionService.buscarPorEstudiante(1)).thenReturn(List.of());
        when(libroService.listId(10)).thenReturn(Optional.of(libro));
        // El estudiante ya tiene muchos prestamos activos, pero como no hay
        // configuracion, el controller no aplica el limite (segun el codigo actual).
        Prestamo activo = new Prestamo();
        activo.setEstado("vigente");
        when(prestamoService.buscarPorEstudiante(1)).thenReturn(List.of(activo, activo, activo));
        when(configuracionService.obtener()).thenReturn(Optional.empty());
        when(usuarioService.buscarPorRol("BIBLIOTECARIO")).thenReturn(List.of());
        when(prestamoService.insert(any(Prestamo.class))).thenAnswer(inv -> inv.getArgument(0));

        LocalDateTime antes = LocalDateTime.now();
        ResponseEntity<?> respuesta = prestamoController.solicitar(dtoSolicitar());

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());

        ArgumentCaptor<Prestamo> captor = ArgumentCaptor.forClass(Prestamo.class);
        verify(prestamoService).insert(captor.capture());
        long dias = ChronoUnit.DAYS.between(antes, captor.getValue().getFechaEntrega());
        assertTrue(dias >= 14 && dias <= 15, "sin configuracion debe usarse el default de 15 dias");
    }

    // ---------- aprobar ----------

    @Test
    void aprobar_prestamoNoExiste_retorna404() {
        when(prestamoService.listId(1)).thenReturn(Optional.empty());

        ResponseEntity<String> respuesta = prestamoController.aprobar(1);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void aprobar_prestamoNoEstaSolicitado_retorna400() {
        Prestamo prestamo = new Prestamo();
        prestamo.setEstado("vigente");
        when(prestamoService.listId(1)).thenReturn(Optional.of(prestamo));

        ResponseEntity<String> respuesta = prestamoController.aprobar(1);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
    }

    @Test
    void aprobar_libroSinStock_retorna400() {
        libro.setStock(0);
        Prestamo prestamo = new Prestamo();
        prestamo.setEstado("solicitado");
        prestamo.setLibro(libro);
        prestamo.setEstudiante(estudiante);
        when(prestamoService.listId(1)).thenReturn(Optional.of(prestamo));

        ResponseEntity<String> respuesta = prestamoController.aprobar(1);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        verify(libroService, never()).edit(any());
    }

    @Test
    void aprobar_exitoso_bajaStockCambiaEstadoYNotificaRecogerEnBiblioteca() {
        libro.setRecurso(null);
        Prestamo prestamo = new Prestamo();
        prestamo.setEstado("solicitado");
        prestamo.setLibro(libro);
        prestamo.setEstudiante(estudiante);
        when(prestamoService.listId(1)).thenReturn(Optional.of(prestamo));

        ResponseEntity<String> respuesta = prestamoController.aprobar(1);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, libro.getStock(), "el stock debe bajar en 1 al aprobar");
        assertEquals("vigente", prestamo.getEstado());
        verify(libroService).edit(libro);
        verify(prestamoService).edit(prestamo);

        ArgumentCaptor<Notificacion> captor = ArgumentCaptor.forClass(Notificacion.class);
        verify(notificacionService).insert(captor.capture());
        assertEquals("confirmacion", captor.getValue().getTipo());
        assertTrue(captor.getValue().getMensaje().contains("recogerlo en biblioteca"));
    }

    @Test
    void aprobar_libroVirtual_notificaAccesoAlRecursoVirtual() {
        libro.setRecurso("http://recurso-virtual.com/libro.pdf");
        Prestamo prestamo = new Prestamo();
        prestamo.setEstado("solicitado");
        prestamo.setLibro(libro);
        prestamo.setEstudiante(estudiante);
        when(prestamoService.listId(1)).thenReturn(Optional.of(prestamo));

        prestamoController.aprobar(1);

        ArgumentCaptor<Notificacion> captor = ArgumentCaptor.forClass(Notificacion.class);
        verify(notificacionService).insert(captor.capture());
        assertTrue(captor.getValue().getMensaje().contains("recurso virtual"));
    }

    // ---------- rechazar ----------

    @Test
    void rechazar_prestamoNoExiste_retorna404() {
        when(prestamoService.listId(1)).thenReturn(Optional.empty());

        ResponseEntity<String> respuesta = prestamoController.rechazar(1, null);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void rechazar_prestamoNoEstaSolicitado_retorna400() {
        Prestamo prestamo = new Prestamo();
        prestamo.setEstado("vigente");
        when(prestamoService.listId(1)).thenReturn(Optional.of(prestamo));

        ResponseEntity<String> respuesta = prestamoController.rechazar(1, null);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
    }

    @Test
    void rechazar_exitoso_conMotivo_notificaIncluyendoElMotivo() {
        Prestamo prestamo = new Prestamo();
        prestamo.setEstado("solicitado");
        prestamo.setLibro(libro);
        prestamo.setEstudiante(estudiante);
        when(prestamoService.listId(1)).thenReturn(Optional.of(prestamo));

        Map<String, String> body = new HashMap<>();
        body.put("motivo", "Libro danado");

        ResponseEntity<String> respuesta = prestamoController.rechazar(1, body);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("rechazado", prestamo.getEstado());
        assertEquals("Libro danado", prestamo.getObservaciones());

        ArgumentCaptor<Notificacion> captor = ArgumentCaptor.forClass(Notificacion.class);
        verify(notificacionService).insert(captor.capture());
        assertEquals("rechazo", captor.getValue().getTipo());
        assertTrue(captor.getValue().getMensaje().contains("Libro danado"));
    }

    @Test
    void rechazar_exitoso_sinMotivo_notificaSinTextoDeMotivo() {
        Prestamo prestamo = new Prestamo();
        prestamo.setEstado("solicitado");
        prestamo.setLibro(libro);
        prestamo.setEstudiante(estudiante);
        when(prestamoService.listId(1)).thenReturn(Optional.of(prestamo));

        ResponseEntity<String> respuesta = prestamoController.rechazar(1, null);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());

        ArgumentCaptor<Notificacion> captor = ArgumentCaptor.forClass(Notificacion.class);
        verify(notificacionService).insert(captor.capture());
        assertFalse(captor.getValue().getMensaje().contains("Motivo:"));
    }

    // ---------- devolver ----------

    private PrestamoDTO dtoDevolver(int idPrestamo) {
        PrestamoDTO dto = new PrestamoDTO();
        dto.setIdPrestamo(idPrestamo);
        dto.setEstadoDevolucion("bueno");
        dto.setObservacionesDev("Sin observaciones");
        return dto;
    }

    @Test
    void devolver_prestamoNoExiste_retorna404() {
        when(prestamoService.listId(1)).thenReturn(Optional.empty());

        ResponseEntity<?> respuesta = prestamoController.devolver(dtoDevolver(1));

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void devolver_estadoInvalido_retorna400() {
        Prestamo prestamo = new Prestamo();
        prestamo.setEstado("solicitado");
        when(prestamoService.listId(1)).thenReturn(Optional.of(prestamo));

        ResponseEntity<?> respuesta = prestamoController.devolver(dtoDevolver(1));

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
    }

    @Test
    void devolver_sinRetraso_noGeneraSancionYSubeStock() {
        libro.setStock(1);
        libro.setStockTotal(3);
        Prestamo prestamo = new Prestamo();
        prestamo.setEstado("vigente");
        prestamo.setLibro(libro);
        prestamo.setEstudiante(estudiante);
        prestamo.setFechaEntrega(LocalDateTime.now().plusDays(1)); // aun no vence
        when(prestamoService.listId(1)).thenReturn(Optional.of(prestamo));

        ResponseEntity<?> respuesta = prestamoController.devolver(dtoDevolver(1));

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(2, libro.getStock(), "el stock debe subir en 1 al devolver");
        assertEquals("devuelto", prestamo.getEstado());
        verify(sancionService, never()).insert(any(Sancion.class));
        verify(notificacionService, never()).insert(any(Notificacion.class));
    }

    @Test
    void devolver_stockYaAlTope_noIncrementaStock() {
        libro.setStock(3);
        libro.setStockTotal(3);
        Prestamo prestamo = new Prestamo();
        prestamo.setEstado("vigente");
        prestamo.setLibro(libro);
        prestamo.setEstudiante(estudiante);
        prestamo.setFechaEntrega(LocalDateTime.now().plusDays(1));
        when(prestamoService.listId(1)).thenReturn(Optional.of(prestamo));

        prestamoController.devolver(dtoDevolver(1));

        assertEquals(3, libro.getStock());
        verify(libroService, never()).edit(libro);
    }

    @Test
    void devolver_conRetraso_generaSancionConMultaSegunConfiguracion() {
        libro.setStock(1);
        libro.setStockTotal(3);
        Prestamo prestamo = new Prestamo();
        prestamo.setEstado("vigente");
        prestamo.setLibro(libro);
        prestamo.setEstudiante(estudiante);
        prestamo.setFechaEntrega(LocalDateTime.now().minusDays(3));
        when(prestamoService.listId(1)).thenReturn(Optional.of(prestamo));

        ConfiguracionBiblioteca config = new ConfiguracionBiblioteca();
        config.setMultaPorDia(2.5);
        when(configuracionService.obtener()).thenReturn(Optional.of(config));

        ResponseEntity<?> respuesta = prestamoController.devolver(dtoDevolver(1));

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());

        ArgumentCaptor<Sancion> captor = ArgumentCaptor.forClass(Sancion.class);
        verify(sancionService).insert(captor.capture());
        Sancion sancion = captor.getValue();
        assertEquals(3, sancion.getDiasSuspension());
        assertEquals(7.5, sancion.getMulta(), 0.001);
        assertEquals("activa", sancion.getEstado());

        ArgumentCaptor<Notificacion> notifCaptor = ArgumentCaptor.forClass(Notificacion.class);
        verify(notificacionService).insert(notifCaptor.capture());
        assertEquals("sancion", notifCaptor.getValue().getTipo());
    }

    @Test
    void devolver_conRetrasoSinConfiguracion_usaMultaPorDiaPorDefectoDeUnoPorDia() {
        libro.setStock(1);
        libro.setStockTotal(3);
        Prestamo prestamo = new Prestamo();
        prestamo.setEstado("vigente");
        prestamo.setLibro(libro);
        prestamo.setEstudiante(estudiante);
        prestamo.setFechaEntrega(LocalDateTime.now().minusDays(2));
        when(prestamoService.listId(1)).thenReturn(Optional.of(prestamo));
        when(configuracionService.obtener()).thenReturn(Optional.empty());

        prestamoController.devolver(dtoDevolver(1));

        ArgumentCaptor<Sancion> captor = ArgumentCaptor.forClass(Sancion.class);
        verify(sancionService).insert(captor.capture());
        assertEquals(2.0, captor.getValue().getMulta(), 0.001);
    }
}
