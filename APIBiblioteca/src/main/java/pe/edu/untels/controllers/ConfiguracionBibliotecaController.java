package pe.edu.untels.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.untels.dtos.ConfiguracionBibliotecaDTO;
import pe.edu.untels.entities.ConfiguracionBiblioteca;
import pe.edu.untels.servicesinterfaces.IConfiguracionBibliotecaService;

import java.util.Optional;

@RestController
@RequestMapping("/api/configuracion")
public class ConfiguracionBibliotecaController {

    @Autowired
    private IConfiguracionBibliotecaService configuracionService;

    @GetMapping
    public ResponseEntity<?> obtener() {
        ModelMapper mapper = new ModelMapper();
        Optional<ConfiguracionBiblioteca> config = configuracionService.obtener();

        if (config.isPresent()) {
            ConfiguracionBibliotecaDTO dto = mapper.map(config.get(), ConfiguracionBibliotecaDTO.class);
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Configuracion no encontrada");
    }

    @PutMapping("/actualiza")
    public ResponseEntity<String> actualizar(@RequestBody ConfiguracionBibliotecaDTO dto) {
        Optional<ConfiguracionBiblioteca> existente = configuracionService.obtener();

        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Configuracion no encontrada");
        }

        ConfiguracionBiblioteca config = existente.get();
        config.setDiasMaxPrestamo(dto.getDiasMaxPrestamo());
        config.setLimitePrestamos(dto.getLimitePrestamos());
        config.setMultaPorDia(dto.getMultaPorDia());
        config.setSchedulerActivo(dto.isSchedulerActivo());
        config.setNotifEmail(dto.isNotifEmail());
        config.setAlertaStock(dto.isAlertaStock());
        config.setModoMant(dto.isModoMant());

        configuracionService.actualizar(config);

        return ResponseEntity.ok("Configuracion actualizada correctamente");
    }
}
