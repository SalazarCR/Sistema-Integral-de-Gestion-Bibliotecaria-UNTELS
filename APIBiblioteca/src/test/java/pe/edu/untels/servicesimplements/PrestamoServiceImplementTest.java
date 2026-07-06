package pe.edu.untels.servicesimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.untels.entities.Libro;
import pe.edu.untels.entities.Prestamo;
import pe.edu.untels.entities.Usuario;
import pe.edu.untels.repositories.IPrestamoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias de PrestamoServiceImplement.
 *
 * IMPORTANTE (hallazgo de diseno): esta clase solo contiene operaciones CRUD y
 * busquedas basicas que delegan directo al repositorio. Las reglas de negocio
 * reales de prestamos (bloqueo por sancion activa, control de stock, limite de
 * prestamos simultaneos, calculo de multa por retraso, notificaciones, etc.)
 * NO estan implementadas aqui sino en pe.edu.untels.controllers.PrestamoController.
 * Esas reglas se prueban en PrestamoControllerTest.
 */
@ExtendWith(MockitoExtension.class)
class PrestamoServiceImplementTest {

    @Mock
    private IPrestamoRepository prestamoRepository;

    @InjectMocks
    private PrestamoServiceImplement prestamoService;

    private Prestamo prestamo;

    @BeforeEach
    void setUp() {
        Libro libro = new Libro();
        libro.setIdLibro(10);

        Usuario estudiante = new Usuario();
        estudiante.setIdUsuario(20);

        prestamo = new Prestamo();
        prestamo.setIdPrestamo(1);
        prestamo.setLibro(libro);
        prestamo.setEstudiante(estudiante);
        prestamo.setEstado("solicitado");
    }

    @Test
    void list_devuelveTodosLosPrestamos() {
        when(prestamoRepository.findAll()).thenReturn(List.of(prestamo));

        List<Prestamo> resultado = prestamoService.list();

        assertEquals(1, resultado.size());
    }

    @Test
    void insert_delegaEnRepositoryYDevuelveElGuardado() {
        when(prestamoRepository.save(prestamo)).thenReturn(prestamo);

        Prestamo guardado = prestamoService.insert(prestamo);

        assertEquals(prestamo, guardado);
        verify(prestamoRepository).save(prestamo);
    }

    @Test
    void listId_encontrado() {
        when(prestamoRepository.findById(1)).thenReturn(Optional.of(prestamo));

        Optional<Prestamo> resultado = prestamoService.listId(1);

        assertTrue(resultado.isPresent());
    }

    @Test
    void listId_noEncontrado() {
        when(prestamoRepository.findById(99)).thenReturn(Optional.empty());

        assertTrue(prestamoService.listId(99).isEmpty());
    }

    @Test
    void edit_delegaEnRepositorySave() {
        prestamoService.edit(prestamo);

        verify(prestamoRepository).save(prestamo);
    }

    @Test
    void delete_delegaEnRepositoryDeleteById() {
        prestamoService.delete(1);

        verify(prestamoRepository).deleteById(1);
    }

    @Test
    void buscarPorEstado_delegaEnFindByEstado() {
        when(prestamoRepository.findByEstado("vigente")).thenReturn(List.of(prestamo));

        List<Prestamo> resultado = prestamoService.buscarPorEstado("vigente");

        assertEquals(1, resultado.size());
        verify(prestamoRepository).findByEstado("vigente");
    }

    @Test
    void buscarPorEstudiante_delegaEnFindByEstudianteIdUsuario() {
        when(prestamoRepository.findByEstudianteIdUsuario(20)).thenReturn(List.of(prestamo));

        List<Prestamo> resultado = prestamoService.buscarPorEstudiante(20);

        assertEquals(1, resultado.size());
        verify(prestamoRepository).findByEstudianteIdUsuario(20);
    }

    @Test
    void buscarPorLibro_delegaEnFindByLibroIdLibro() {
        when(prestamoRepository.findByLibroIdLibro(10)).thenReturn(List.of(prestamo));

        List<Prestamo> resultado = prestamoService.buscarPorLibro(10);

        assertEquals(1, resultado.size());
        verify(prestamoRepository).findByLibroIdLibro(10);
    }
}
