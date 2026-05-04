package pe.edu.untels.config;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        PasswordEncoder encoder = passwordEncoder();
        log.info(">>> [CONFIG] PasswordEncoder creado: {}", encoder.getClass().getSimpleName());
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
            .authenticationProvider(authProvider())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/login", "/api/auth/login").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .permitAll()
                .successHandler((request, response, authentication) -> {
                    String username = authentication.getName();
                    String roles = authentication.getAuthorities().toString();
                    log.info(">>> [LOGIN] ✅ AUTENTICACIÓN EXITOSA — usuario: '{}' — roles: {}", username, roles);
                    response.sendRedirect("/swagger-ui.html");
                })
                .failureHandler((request, response, exception) -> {
                    log.warn(">>> [LOGIN] ❌ AUTENTICACIÓN FALLIDA — motivo: {}", exception.getMessage());
                    response.sendRedirect("/login?error");
                })
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
