package pe.edu.untels.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.untels.entities.User;
import pe.edu.untels.repositories.IUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(">>> [LOGIN] Username recibido: '{}'", username);

        Optional<User> userOpt = userRepository.findByUsernameUser(username);

        if (userOpt.isEmpty()) {
            log.warn(">>> [LOGIN] Usuario NO encontrado en BD: '{}'", username);
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        User user = userOpt.get();
        String storedPassword = user.getPasswordUser();

        log.info(">>> [LOGIN] Usuario encontrado: '{}'", user.getUsernameUser());
        log.info(">>> [LOGIN] status_user (enabled): {}", user.isStatusUser());
        log.info(">>> [LOGIN] Rol: '{}'", user.getRole().getNameRole());
        log.info(">>> [LOGIN] password_user length en BD: {}", storedPassword == null ? "NULL" : storedPassword.length());
        log.info(">>> [LOGIN] password_user equals '1234': {}", "1234".equals(storedPassword));

        return new org.springframework.security.core.userdetails.User(
                user.getUsernameUser(),
                storedPassword,
                user.isStatusUser(),
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getNameRole()))
        );
    }
}

