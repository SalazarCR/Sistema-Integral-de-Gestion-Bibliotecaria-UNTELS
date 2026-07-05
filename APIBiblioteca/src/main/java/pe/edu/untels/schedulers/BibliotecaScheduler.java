package pe.edu.untels.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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
import java.util.Optional;

@Component
public class BibliotecaScheduler {

    @Autowired
    private IConfiguracionBibliotecaService configuracionService;

    @Autowired
    private IPrestamoService prestamoService;

    @Autowired
    private ISancionService sancionService;

    @Autowired
    private ILibroService libroService;

    @Autowired
    private INotificacionService notificacionService;

    @Autowired
    private IUsuarioService usuarioService;

    @Scheduled(cron = "0 0 1 * * *")
    public void ejecutarTareasDiarias() {
        Optional<ConfiguracionBiblioteca> configOpt = configuracionService.obtener();
        if (configOpt.isEmpty() || !configOpt.get().isSchedulerActivo()) {
            return;
        }

        ConfiguracionBiblioteca config = configOpt.get();
        LocalDateTime ahora = LocalDateTime.now();

        for (Prestamo prestamo : prestamoService.buscarVigentesVencidos(ahora)) {
            prestamo.setEstado("vencido");
            prestamoService.edit(prestamo);
        }

        for (Sancion sancion : sancionService.buscarActivasVencidas(ahora)) {
            sancion.setEstado("cumplida");
            sancionService.edit(sancion);
        }

        if (config.isAlertaStock()) {
            for (Libro libro : libroService.buscarConStockBajo(2)) {
                for (Usuario admin : usuarioService.buscarPorRol("ADMIN")) {
                    Notificacion notificacion = new Notificacion();
                    notificacion.setEstudiante(admin);
                    notificacion.setTipo("stock");
                    notificacion.setMensaje("Stock critico: '" + libro.getTitulo() + "' tiene " + libro.getStock() + " ejemplar(es) disponible(s)");
                    notificacion.setFecha(ahora);
                    notificacion.setLeida(false);
                    notificacionService.insert(notificacion);
                }
            }
        }
    }
}
