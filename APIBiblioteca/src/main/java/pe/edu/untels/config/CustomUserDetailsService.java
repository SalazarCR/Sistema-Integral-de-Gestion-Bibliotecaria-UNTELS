package pe.edu.untels.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.untels.entities.User;
import pe.edu.untels.repositories.IUserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
/**
 * Servicio personalizado para cargar usuarios desde la base de datos
 * para la autenticación en el sistema de biblioteca.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByUsernameUser(username);

        if (!userOpt.isPresent()) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        User user = userOpt.get();

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getNameRole()));

        return new org.springframework.security.core.userdetails.User(
            user.getUsernameUser(),
            user.getPasswordUser(),
            user.getStatusUser(),
            true,
            true,
            true,
            authorities
        );
    }
}

