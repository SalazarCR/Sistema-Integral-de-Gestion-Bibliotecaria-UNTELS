package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Optional<User> userOpt = userRepository.findByUsernameUser(loginRequestDTO.getUsernameUser());
        if (userOpt.isPresent() && userOpt.get().getPasswordUser().equals(loginRequestDTO.getPasswordUser())) {
            User user = userOpt.get();
            if (user.isStatusUser()) {
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
}

