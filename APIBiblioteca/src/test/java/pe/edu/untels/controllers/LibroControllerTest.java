package pe.edu.untels.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.edu.untels.dtos.LibroDTO;
import pe.edu.untels.entities.Libro;
import pe.edu.untels.servicesinterfaces.ILibroService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias de LibroController, centradas en registrar() (bloqueo de ISBN
 * duplicado con 409 CONFLICT) y registrarPorIsbn() (delegacion al servicio de
 * registro automatico via API externa).
 */
@ExtendWith(MockitoExtension.class)
class LibroControllerTest {

    @Mock
    private ILibroService libroService;

    @InjectMocks
    private LibroController libroController;

    private LibroDTO dtoNuevoLibro() {
        LibroDTO dto = new LibroDTO();
        dto.setTitulo("Clean Code");
        dto.setAutor("Robert C. Martin");
        dto.setIsbn("9780132350884");
        dto.setEditorial("Prentice Hall");
        dto.setCategoria("Software");
        dto.setStock(5);
        return dto;
    }

    @Test
    void registrar_isbnDuplicado_retorna409YNoGuarda() {
        LibroDTO dto = dtoNuevoLibro();
        when(libroService.existeIsbn("9780132350884")).thenReturn(true);

        ResponseEntity<?> respuesta = libroController.registrar(dto);

        assertEquals(HttpStatus.CONFLICT, respuesta.getStatusCode());
        verify(libroService, never()).insert(any(Libro.class));
    }

    @Test
    void registrar_stockTotalNoEnviado_seCompletaConElStockInicial() {
        LibroDTO dto = dtoNuevoLibro();
        dto.setStockTotal(0);
        when(libroService.existeIsbn("9780132350884")).thenReturn(false);
        when(libroService.insert(any(Libro.class))).thenAnswer(inv -> inv.getArgument(0));

        libroController.registrar(dto);

        ArgumentCaptor<Libro> captor = ArgumentCaptor.forClass(Libro.class);
        verify(libroService).insert(captor.capture());
        assertEquals(5, captor.getValue().getStockTotal(),
                "si no se envia stockTotal, debe copiarse del stock inicial");
    }

    @Test
    void registrar_stockTotalEnviado_seRespetaElValorRecibido() {
        LibroDTO dto = dtoNuevoLibro();
        dto.setStockTotal(10);
        when(libroService.existeIsbn("9780132350884")).thenReturn(false);
        when(libroService.insert(any(Libro.class))).thenAnswer(inv -> inv.getArgument(0));

        libroController.registrar(dto);

        ArgumentCaptor<Libro> captor = ArgumentCaptor.forClass(Libro.class);
        verify(libroService).insert(captor.capture());
        assertEquals(10, captor.getValue().getStockTotal());
    }

    @Test
    void registrar_exitoso_retorna201() {
        LibroDTO dto = dtoNuevoLibro();
        when(libroService.existeIsbn("9780132350884")).thenReturn(false);
        when(libroService.insert(any(Libro.class))).thenAnswer(inv -> inv.getArgument(0));

        ResponseEntity<?> respuesta = libroController.registrar(dto);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
    }

    @Test
    void registrarPorIsbn_exitoso_retorna201ConElLibroCreado() {
        Libro libro = new Libro();
        libro.setIdLibro(1);
        libro.setTitulo("Effective Java");
        libro.setIsbn("9780134685991");
        when(libroService.registrarLibroPorIsbn("9780134685991")).thenReturn(libro);

        ResponseEntity<?> respuesta = libroController.registrarPorIsbn("9780134685991");

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
    }

    @Test
    void registrarPorIsbn_isbnDuplicado_retorna400ConElMensajeDeLaExcepcion() {
        when(libroService.registrarLibroPorIsbn(anyString()))
                .thenThrow(new RuntimeException("El libro con ISBN 123 ya existe en la biblioteca"));

        ResponseEntity<?> respuesta = libroController.registrarPorIsbn("123");

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals("El libro con ISBN 123 ya existe en la biblioteca", respuesta.getBody());
    }
}
