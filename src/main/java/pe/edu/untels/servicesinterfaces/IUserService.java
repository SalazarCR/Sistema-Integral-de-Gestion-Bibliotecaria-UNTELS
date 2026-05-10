package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.dtos.LoginRequestDTO;
import pe.edu.untels.dtos.LoginResponseDTO;
import pe.edu.untels.entities.User;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
    User createUser(User user);
    Optional<User> getUserById(int idUser);
    User updateUser(User user);
    void deleteUser(int idUser);
    List<User> listUsers();
    boolean isUserActive(int idUser);
    User toggleUserStatus(int idUser);
}

