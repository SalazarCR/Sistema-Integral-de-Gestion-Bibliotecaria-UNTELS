package pe.edu.untels.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pe.edu.untels.entities.ConfiguracionBiblioteca;
import pe.edu.untels.entities.Notificacion;
import pe.edu.untels.entities.Prestamo;
import pe.edu.untels.servicesinterfaces.IConfiguracionBibliotecaService;
import pe.edu.untels.servicesinterfaces.INotificacionService;
import pe.edu.untels.servicesinterfaces.IPrestamoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Revisa periodicamente los prestamos vigentes para:
 * - Marcar como "vencido" los que superaron la fechaEntrega.
 * - Enviar un recordatorio cuando falta poco tiempo para la fecha limite.
 * Solo corre si la configuracion de biblioteca tiene el scheduler activado.
 */
@Component
public class PrestamoRecordatorioScheduler {

    @Autowired
    private IPrestamoService prestamoService;

    @Autowired
    private INotificacionService notificacionService;

    @Autowired
    private IConfiguracionBibliotecaService configuracionService;

    @Scheduled(fixedRate = 3600000) // cada hora
    public void revisarPrestamosVigentes() {
        Optional<ConfiguracionBiblioteca> configOpt = configuracionService.obtener();
        if (configOpt.isEmpty() || !configOpt.get().isSchedulerActivo()) {
            return;
        }

        LocalDateTime ahora = LocalDateTime.now();
        List<Prestamo> vigentes = prestamoService.buscarPorEstado("vigente");

        for (Prestamo prestamo : vigentes) {
            if (prestamo.getFechaEntrega() == null) {
                continue;
            }

            if (prestamo.getFechaEntrega().isBefore(ahora)) {
                marcarVencido(prestamo);
            } else if (prestamo.getFechaEntrega().isBefore(ahora.plusDays(1))) {
                enviarRecordatorio(prestamo);
            }
        }
    }

    private void marcarVencido(Prestamo prestamo) {
        prestamo.setEstado("vencido");
        prestamoService.edit(prestamo);

        Notificacion notificacion = new Notificacion();
        notificacion.setEstudiante(prestamo.getEstudiante());
        notificacion.setTipo("vencido");
        notificacion.setMensaje("Tu prestamo del libro '" + prestamo.getLibro().getTitulo()
                + "' esta vencido. Devuelvelo cuanto antes para evitar una sancion mayor.");
        notificacion.setFecha(LocalDateTime.now());
        notificacion.setLeida(false);
        notificacionService.insert(notificacion);
    }

    private void enviarRecordatorio(Prestamo prestamo) {
        String tituloLibro = prestamo.getLibro().getTitulo();

        boolean yaNotificadoHoy = notificacionService.buscarPorEstudiante(prestamo.getEstudiante().getIdUsuario())
                .stream()
                .filter(n -> "recordatorio".equals(n.getTipo()))
                .filter(n -> n.getFecha().isAfter(LocalDateTime.now().minusHours(20)))
                .anyMatch(n -> n.getMensaje().contains(tituloLibro));

        if (yaNotificadoHoy) {
            return;
        }

        Notificacion notificacion = new Notificacion();
        notificacion.setEstudiante(prestamo.getEstudiante());
        notificacion.setTipo("recordatorio");
        notificacion.setMensaje("Falta menos de 1 dia para la fecha limite de devolucion del libro '"
                + tituloLibro + "'.");
        notificacion.setFecha(LocalDateTime.now());
        notificacion.setLeida(false);
        notificacionService.insert(notificacion);
    }
}
