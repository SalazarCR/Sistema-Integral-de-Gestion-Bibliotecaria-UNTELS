package pe.edu.untels.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.edu.untels.entities.Sancion;
import pe.edu.untels.servicesinterfaces.ISancionService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias de SancionController.cumplir(), que es donde vive la
 * transicion de estado "activa" -> "cumplida" (ISancionService/SancionServiceImplement
 * solo hacen CRUD puro).
 */
@ExtendWith(MockitoExtension.class)
class SancionControllerTest {

    @Mock
    private ISancionService sancionService;

    @InjectMocks
    private SancionController sancionController;

    @Test
    void cumplir_sancionNoExiste_retorna404() {
        when(sancionService.listId(1)).thenReturn(Optional.empty());

        ResponseEntity<String> respuesta = sancionController.cumplir(1);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void cumplir_sancionNoActiva_retorna400YNoLaModifica() {
        Sancion sancion = new Sancion();
        sancion.setEstado("cumplida");
        when(sancionService.listId(1)).thenReturn(Optional.of(sancion));

        ResponseEntity<String> respuesta = sancionController.cumplir(1);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals("cumplida", sancion.getEstado());
        verify(sancionService, never()).edit(sancion);
    }

    @Test
    void cumplir_sancionActiva_laMarcaComoCumplida() {
        Sancion sancion = new Sancion();
        sancion.setEstado("activa");
        when(sancionService.listId(1)).thenReturn(Optional.of(sancion));

        ResponseEntity<String> respuesta = sancionController.cumplir(1);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("cumplida", sancion.getEstado());
        verify(sancionService).edit(sancion);
    }
}
