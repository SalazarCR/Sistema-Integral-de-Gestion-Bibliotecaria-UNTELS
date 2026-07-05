package pe.edu.untels.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.untels.dtos.SancionDTO;
import pe.edu.untels.entities.Sancion;
import pe.edu.untels.servicesinterfaces.ISancionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sanciones")
public class SancionController {

    @Autowired
    private ISancionService sancionService;

    @GetMapping("/lista")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<List<SancionDTO>> listar() {
        ModelMapper mapper = new ModelMapper();

        List<SancionDTO> lista = sancionService.list()
                .stream()
                .map(sancion -> mapper.map(sancion, SancionDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<List<SancionDTO>> buscarPorEstado(@PathVariable String estado) {
        ModelMapper mapper = new ModelMapper();

        List<SancionDTO> lista = sancionService.buscarPorEstado(estado)
                .stream()
                .map(sancion -> mapper.map(sancion, SancionDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/estudiante/{idEstudiante}")
    public ResponseEntity<List<SancionDTO>> buscarPorEstudiante(@PathVariable int idEstudiante) {
        ModelMapper mapper = new ModelMapper();

        List<SancionDTO> lista = sancionService.buscarPorEstudiante(idEstudiante)
                .stream()
                .map(sancion -> mapper.map(sancion, SancionDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/cumplir/{idSancion}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BIBLIOTECARIO')")
    public ResponseEntity<String> cumplir(@PathVariable int idSancion) {
        Optional<Sancion> sancionOpt = sancionService.listId(idSancion);

        if (sancionOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sancion no encontrada");
        }

        Sancion sancion = sancionOpt.get();

        if (!sancion.getEstado().equals("activa")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La sancion no esta activa");
        }

        sancion.setEstado("cumplida");
        sancionService.edit(sancion);

        return ResponseEntity.ok("Sancion cumplida correctamente");
    }
}
