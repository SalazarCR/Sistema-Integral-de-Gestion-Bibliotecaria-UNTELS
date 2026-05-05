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
    public LoginResponseDTO login(LoginRequestDTO dto) {

        Optional<User> userOpt =
                userRepository.findByUsernameUser(dto.getUsername());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        User user = userOpt.get();

        if (!user.getPasswordUser().equals(dto.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (!user.isStatusUser()) {
            throw new RuntimeException("Usuario deshabilitado");
        }

        return LoginResponseDTO.ok(
                user.getIdUser(),
                user.getUsernameUser(),
                user.getEmailUser(),
                user.getRole().getNameRole()
        );
    }

    @Override
    public User createUser(User user) {
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
        return userRepository.findById(idUser)
                .map(User::isStatusUser)
                .orElse(false);
    }

    @Override
    public User toggleUserStatus(int idUser) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setStatusUser(!user.isStatusUser());
        return userRepository.save(user);
    }
}