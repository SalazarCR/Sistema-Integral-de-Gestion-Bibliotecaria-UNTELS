package pe.edu.untels.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login",
                                "/error",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // Catalogo de libros: lectura para cualquier rol autenticado, escritura solo bibliotecario/admin
                        .requestMatchers(HttpMethod.GET, "/api/libros/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/libros/**").hasAnyRole("ADMIN", "BIBLIOTECARIO")
                        .requestMatchers(HttpMethod.PUT, "/api/libros/**").hasAnyRole("ADMIN", "BIBLIOTECARIO")
                        .requestMatchers(HttpMethod.DELETE, "/api/libros/**").hasAnyRole("ADMIN", "BIBLIOTECARIO")

                        // Usuarios / padron: gestionado por admin y bibliotecario
                        .requestMatchers("/api/usuarios/**").hasAnyRole("ADMIN", "BIBLIOTECARIO")

                        // Prestamos: el estudiante solo solicita; aprobar/rechazar/devolver es de bibliotecario/admin
                        .requestMatchers(HttpMethod.POST, "/api/prestamos/solicitar").hasRole("ESTUDIANTE")
                        .requestMatchers(HttpMethod.PUT, "/api/prestamos/aprobar/**", "/api/prestamos/rechazar/**", "/api/prestamos/devolver")
                                .hasAnyRole("ADMIN", "BIBLIOTECARIO")
                        .requestMatchers(HttpMethod.GET, "/api/prestamos/lista", "/api/prestamos/estado/**")
                                .hasAnyRole("ADMIN", "BIBLIOTECARIO")
                        .requestMatchers(HttpMethod.GET, "/api/prestamos/**").authenticated()

                        // Sanciones: consulta general autenticada, gestion (listar todas/cumplir) de bibliotecario/admin
                        .requestMatchers(HttpMethod.GET, "/api/sanciones/lista", "/api/sanciones/estado/**")
                                .hasAnyRole("ADMIN", "BIBLIOTECARIO")
                        .requestMatchers(HttpMethod.PUT, "/api/sanciones/cumplir/**").hasAnyRole("ADMIN", "BIBLIOTECARIO")
                        .requestMatchers(HttpMethod.GET, "/api/sanciones/**").authenticated()

                        // Notificaciones: cada usuario consulta las suyas
                        .requestMatchers("/api/notificaciones/**").authenticated()

                        // Configuracion: lectura para admin/bibliotecario, escritura solo admin
                        .requestMatchers(HttpMethod.GET, "/api/configuracion").hasAnyRole("ADMIN", "BIBLIOTECARIO")
                        .requestMatchers(HttpMethod.PUT, "/api/configuracion/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
