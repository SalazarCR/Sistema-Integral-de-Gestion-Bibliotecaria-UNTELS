package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.untels.dtos.LoginRequestDTO;
import pe.edu.untels.dtos.LoginResponseDTO;
import pe.edu.untels.entities.User;
import pe.edu.untels.repositories.IUserRepository;
import pe.edu.untels.servicesinterfaces.IUserService;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Optional<User> userOpt = userRepository.findByUsernameUser(loginRequestDTO.getUsernameUser());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.isStatusUser() && passwordEncoder.matches(loginRequestDTO.getPasswordUser(), user.getPasswordUser())) {
                return new LoginResponseDTO(user.getIdUser(), user.getUsernameUser(), user.getEmailUser(), user.getRole().getNameRole(), user.isStatusUser());
            }
        }
        throw new RuntimeException("Credenciales inválidas");
    }

    @Override
    public User createUser(User user) {
        if (userRepository.existsByUsernameUser(user.getUsernameUser())) {
            throw new RuntimeException("El usuario ya existe");
        }
        // Encriptar la contraseña antes de guardar
        user.setPasswordUser(passwordEncoder.encode(user.getPasswordUser()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(int idUser) {
        return userRepository.findById(idUser);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int idUser) {
        userRepository.deleteById(idUser);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean isUserActive(int idUser) {
        Optional<User> userOpt = userRepository.findById(idUser);
        return userOpt.isPresent() && userOpt.get().isStatusUser();
    }

    @Override
    public User toggleUserStatus(int idUser) {
        Optional<User> userOpt = userRepository.findById(idUser);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        User user = userOpt.get();
        user.setStatusUser(!user.isStatusUser());
        return userRepository.save(user);
    }
}




