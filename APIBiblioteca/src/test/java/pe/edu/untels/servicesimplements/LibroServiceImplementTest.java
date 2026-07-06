package pe.edu.untels.servicesimplements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.untels.dtos.LibroApiExternaDTO;
import pe.edu.untels.entities.Libro;
import pe.edu.untels.repositories.ILibroRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias de LibroServiceImplement, mockeando ILibroRepository.
 * Cubre CRUD basico, busquedas y la logica de registrarLibroPorIsbn()
 * (incluyendo el bloqueo de ISBN duplicado).
 */
@ExtendWith(MockitoExtension.class)
class LibroServiceImplementTest {

    @Mock
    private ILibroRepository libroRepository;

    @InjectMocks
    private LibroServiceImplement libroService;

    private Libro libro;

    @BeforeEach
    void setUp() {
        libro = new Libro();
        libro.setIdLibro(1);
        libro.setTitulo("Clean Code");
        libro.setAutor("Robert C. Martin");
        libro.setIsbn("9780132350884");
        libro.setCategoria("Software");
        libro.setStock(3);
        libro.setStockTotal(3);
    }

    @Test
    void list_devuelveTodosLosLibrosDelRepositorio() {
        when(libroRepository.findAll()).thenReturn(List.of(libro));

        List<Libro> resultado = libroService.list();

        assertEquals(1, resultado.size());
        assertEquals("Clean Code", resultado.get(0).getTitulo());
    }

    @Test
    void insert_delegaEnRepositoryYDevuelveElGuardado() {
        when(libroRepository.save(libro)).thenReturn(libro);

        Libro guardado = libroService.insert(libro);

        assertEquals(libro, guardado);
        verify(libroRepository).save(libro);
    }

    @Test
    void listId_encontrado_devuelveOptionalConLibro() {
        when(libroRepository.findById(1)).thenReturn(Optional.of(libro));

        Optional<Libro> resultado = libroService.listId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Clean Code", resultado.get().getTitulo());
    }

    @Test
    void listId_noEncontrado_devuelveOptionalVacio() {
        when(libroRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Libro> resultado = libroService.listId(99);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void edit_delegaEnRepositorySave() {
        libroService.edit(libro);

        verify(libroRepository).save(libro);
    }

    @Test
    void delete_delegaEnRepositoryDeleteById() {
        libroService.delete(1);

        verify(libroRepository).deleteById(1);
    }

    @Test
    void buscarPorCategoria_delegaEnFindByCategoria() {
        when(libroRepository.findByCategoria("Software")).thenReturn(List.of(libro));

        List<Libro> resultado = libroService.buscarPorCategoria("Software");

        assertEquals(1, resultado.size());
        verify(libroRepository).findByCategoria("Software");
    }

    @Test
    void buscarPorTitulo_delegaEnFindByTituloContainingIgnoreCase() {
        when(libroRepository.findByTituloContainingIgnoreCase("clean")).thenReturn(List.of(libro));

        List<Libro> resultado = libroService.buscarPorTitulo("clean");

        assertEquals(1, resultado.size());
        verify(libroRepository).findByTituloContainingIgnoreCase("clean");
    }

    @Test
    void existeIsbn_true_cuandoRepositorioLoConfirma() {
        when(libroRepository.existsByIsbn("9780132350884")).thenReturn(true);

        assertTrue(libroService.existeIsbn("9780132350884"));
    }

    @Test
    void existeIsbn_false_cuandoRepositorioNoLoEncuentra() {
        when(libroRepository.existsByIsbn("0000000000000")).thenReturn(false);

        assertFalse(libroService.existeIsbn("0000000000000"));
    }

    @Test
    void registrarLibroPorIsbn_isbnYaExiste_lanzaExcepcionYNoConsultaApiNiGuarda() {
        when(libroRepository.existsByIsbn("9780132350884")).thenReturn(true);

        // Se usa un spy solo para poder verificar que buscarPorIsbnEnApi() (que hace
        // una llamada HTTP real a openlibrary.org) nunca llega a invocarse cuando el
        // ISBN ya existe: el bloqueo de duplicados debe cortar el flujo antes.
        LibroServiceImplement spyService = spy(libroService);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> spyService.registrarLibroPorIsbn("9780132350884"));

        assertTrue(ex.getMessage().contains("ya existe en la biblioteca"));
        verify(spyService, never()).buscarPorIsbnEnApi(anyString());
        verify(libroRepository, never()).save(any(Libro.class));
    }

    @Test
    void registrarLibroPorIsbn_apiNoEncuentraInformacion_lanzaExcepcion() {
        when(libroRepository.existsByIsbn("1111111111111")).thenReturn(false);

        LibroServiceImplement spyService = spy(libroService);
        doReturn(null).when(spyService).buscarPorIsbnEnApi("1111111111111");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> spyService.registrarLibroPorIsbn("1111111111111"));

        assertTrue(ex.getMessage().contains("No se encontró información"));
        verify(libroRepository, never()).save(any(Libro.class));
    }

    @Test
    void registrarLibroPorIsbn_exitoso_completaValoresPorDefectoYGuarda() {
        when(libroRepository.existsByIsbn("2222222222222")).thenReturn(false);

        LibroApiExternaDTO dto = new LibroApiExternaDTO();
        dto.setTitulo("Effective Java");
        dto.setAutor(null); // sin autor: debe usarse "Autor Desconocido"
        dto.setEditorial("Addison-Wesley");
        dto.setAnio(2018);
        dto.setDescripcion("Buenas practicas de Java");
        dto.setPortada("http://portada.jpg");

        LibroServiceImplement spyService = spy(libroService);
        doReturn(dto).when(spyService).buscarPorIsbnEnApi("2222222222222");
        when(libroRepository.save(any(Libro.class))).thenAnswer(inv -> inv.getArgument(0));

        Libro guardado = spyService.registrarLibroPorIsbn("2222222222222");

        assertEquals("Effective Java", guardado.getTitulo());
        assertEquals("Autor Desconocido", guardado.getAutor());
        assertEquals("2222222222222", guardado.getIsbn());
        assertEquals("General", guardado.getCategoria());
        assertEquals(1, guardado.getStock());
        assertEquals(1, guardado.getStockTotal());
        verify(libroRepository, times(1)).save(any(Libro.class));
    }
}
