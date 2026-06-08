package pe.edu.untels.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.untels.dtos.NotificacionDTO;
import pe.edu.untels.entities.Notificacion;
import pe.edu.untels.servicesinterfaces.INotificacionService;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private INotificacionService notificacionService;

    @GetMapping("/estudiante/{idEstudiante}")
    public ResponseEntity<List<NotificacionDTO>> buscarPorEstudiante(@PathVariable int idEstudiante) {
        ModelMapper mapper = new ModelMapper();

        List<NotificacionDTO> lista = notificacionService.buscarPorEstudiante(idEstudiante)
                .stream()
                .map(notificacion -> mapper.map(notificacion, NotificacionDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/estudiante/{idEstudiante}/pendientes")
    public ResponseEntity<List<NotificacionDTO>> buscarPendientes(@PathVariable int idEstudiante) {
        ModelMapper mapper = new ModelMapper();

        List<NotificacionDTO> lista = notificacionService.buscarPendientesPorEstudiante(idEstudiante)
                .stream()
                .map(notificacion -> mapper.map(notificacion, NotificacionDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/marcar-leidas/{idEstudiante}")
    public ResponseEntity<String> marcarLeidas(@PathVariable int idEstudiante) {
        List<Notificacion> pendientes = notificacionService.buscarPendientesPorEstudiante(idEstudiante);

        for (Notificacion notificacion : pendientes) {
            notificacion.setLeida(true);
            notificacionService.edit(notificacion);
        }

        return ResponseEntity.ok("Notificaciones marcadas como leidas");
    }
}
