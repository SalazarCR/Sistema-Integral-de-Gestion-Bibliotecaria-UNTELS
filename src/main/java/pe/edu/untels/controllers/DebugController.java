package pe.edu.untels.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.untels.dtos.ApiResponseDTO;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/debug")
public class DebugController {
    private static final Logger log = LoggerFactory.getLogger(DebugController.class);

    @GetMapping("/auth-info")
    public ApiResponseDTO<?> getAuthInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> info = new HashMap<>();

        if (auth != null) {
            info.put("isAuthenticated", auth.isAuthenticated());
            info.put("username", auth.getName());
            info.put("authorities", auth.getAuthorities().toString());
            info.put("principal", auth.getPrincipal().toString());
            log.info(">>> [DEBUG] Usuario autenticado: {} - Roles: {}", auth.getName(), auth.getAuthorities());
        } else {
            info.put("isAuthenticated", false);
            info.put("securityContext", "No hay contexto de seguridad");
            log.warn(">>> [DEBUG] NO HAY CONTEXTO DE SEGURIDAD");
        }

        return ApiResponseDTO.ok("Información de autenticación actual", info);
    }
}

