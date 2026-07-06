package pe.edu.untels.servicesimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.untels.entities.Sancion;
import pe.edu.untels.entities.Usuario;
import pe.edu.untels.repositories.ISancionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias de SancionServiceImplement.
 *
 * Nota: el calculo de la multa (dias de retraso * multaPorDia configurada) y la
 * creacion de la sancion ocurren en PrestamoController.devolver(), no en este
 * servicio (que solo hace CRUD). Esa logica se prueba en PrestamoControllerTest.
 * La transicion de estado "activa" -> "cumplida" vive en SancionController.cumplir()
 * y se prueba en SancionControllerTest.
 */
@ExtendWith(MockitoExtension.class)
class SancionServiceImplementTest {

    @Mock
    private ISancionRepository sancionRepository;

    @InjectMocks
    private SancionServiceImplement sancionService;

    private Sancion sancion;

    @BeforeEach
    void setUp() {
        Usuario estudiante = new Usuario();
        estudiante.setIdUsuario(5);

        sancion = new Sancion();
        sancion.setIdSancion(1);
        sancion.setEstudiante(estudiante);
        sancion.setMotivo("Devolucion tardia");
        sancion.setDiasSuspension(3);
        sancion.setMulta(9.0);
        sancion.setEstado("activa");
        sancion.setFechaCreacion(LocalDateTime.now());
    }

    @Test
    void list_devuelveTodasLasSanciones() {
        when(sancionRepository.findAll()).thenReturn(List.of(sancion));

        assertEquals(1, sancionService.list().size());
    }

    @Test
    void insert_delegaEnRepositoryYDevuelveElGuardado() {
        when(sancionRepository.save(sancion)).thenReturn(sancion);

        Sancion guardado = sancionService.insert(sancion);

        assertEquals(sancion, guardado);
        verify(sancionRepository).save(sancion);
    }

    @Test
    void listId_encontrado() {
        when(sancionRepository.findById(1)).thenReturn(Optional.of(sancion));

        assertTrue(sancionService.listId(1).isPresent());
    }

    @Test
    void listId_noEncontrado() {
        when(sancionRepository.findById(99)).thenReturn(Optional.empty());

        assertTrue(sancionService.listId(99).isEmpty());
    }

    @Test
    void edit_delegaEnRepositorySave() {
        sancionService.edit(sancion);

        verify(sancionRepository).save(sancion);
    }

    @Test
    void delete_delegaEnRepositoryDeleteById() {
        sancionService.delete(1);

        verify(sancionRepository).deleteById(1);
    }

    @Test
    void buscarPorEstado_delegaEnFindByEstado() {
        when(sancionRepository.findByEstado("activa")).thenReturn(List.of(sancion));

        List<Sancion> resultado = sancionService.buscarPorEstado("activa");

        assertEquals(1, resultado.size());
        verify(sancionRepository).findByEstado("activa");
    }

    @Test
    void buscarPorEstudiante_delegaEnFindByEstudianteIdUsuario() {
        when(sancionRepository.findByEstudianteIdUsuario(5)).thenReturn(List.of(sancion));

        List<Sancion> resultado = sancionService.buscarPorEstudiante(5);

        assertEquals(1, resultado.size());
        verify(sancionRepository).findByEstudianteIdUsuario(5);
    }
}
