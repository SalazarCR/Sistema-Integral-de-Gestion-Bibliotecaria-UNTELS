package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pe.edu.untels.dtos.ApiResponseDTO;
import pe.edu.untels.dtos.UserDTO;
import pe.edu.untels.dtos.UserResponseDTO;
import pe.edu.untels.entities.Role;
import pe.edu.untels.entities.User;
import pe.edu.untels.exceptions.ValidationException;
import pe.edu.untels.repositories.IRoleRepository;
import pe.edu.untels.repositories.IUserRepository;
import pe.edu.untels.servicesinterfaces.IUserService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImplement implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public ApiResponseDTO registrarUsuario(UserDTO userDTO) {
        if (userDTO.getUsernameUser() == null || userDTO.getUsernameUser().trim().isEmpty()) {
            throw new ValidationException("El username es obligatorio");
        }
        if (userDTO.getPasswordUser() == null || userDTO.getPasswordUser().trim().isEmpty()) {
            throw new ValidationException("La contraseña es obligatoria");
        }
        if (userDTO.getIdRole() == null) {
            throw new ValidationException("El rol es obligatorio");
        }

        Optional<Role> roleOpt = roleRepository.findById(userDTO.getIdRole());
        if (!roleOpt.isPresent()) {
            throw new ValidationException("El rol especificado no existe");
        }

        Role role = roleOpt.get();

        if (role.getNameRole().equalsIgnoreCase("ADMINISTRADOR")) {
            throw new ValidationException("No está permitido registrar usuarios con rol ADMINISTRADOR");
        }

        if (userRepository.existsByUsernameUser(userDTO.getUsernameUser().trim())) {
            throw new ValidationException("El username ya está en uso");
        }

        if (userDTO.getEmailUser() != null && !userDTO.getEmailUser().trim().isEmpty()) {
            if (userRepository.existsByEmailUser(userDTO.getEmailUser().trim())) {
                throw new ValidationException("El email ya está registrado");
            }
        }

        if (userDTO.getDniUser() != null && !userDTO.getDniUser().trim().isEmpty()) {
            if (userDTO.getDniUser().trim().length() != 8) {
                throw new ValidationException("El DNI debe tener exactamente 8 dígitos");
            }
            if (userRepository.existsByDniUser(userDTO.getDniUser().trim())) {
                throw new ValidationException("El DNI ya está registrado");
            }
        }

        User user = new User();
        user.setUsernameUser(userDTO.getUsernameUser().trim());
        user.setPasswordUser(userDTO.getPasswordUser());
        user.setEmailUser(userDTO.getEmailUser() != null ? userDTO.getEmailUser().trim() : null);
        user.setDniUser(userDTO.getDniUser() != null ? userDTO.getDniUser().trim() : null);
        user.setNombreUser(userDTO.getNombreUser());
        user.setApellidoUser(userDTO.getApellidoUser());
        user.setRole(role);
        user.setStatusUser(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User saved = userRepository.save(user);

        return new ApiResponseDTO(true, "Usuario registrado correctamente", mapToResponse(saved), 201);
    }

    @Override
    public ApiResponseDTO listarUsuarios(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<User> paginaUsuarios = userRepository.findAll(pageable);

        List<UserResponseDTO> lista = paginaUsuarios.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        Map<String, Object> data = new HashMap<>();
        data.put("usuarios", lista);
        data.put("totalElementos", paginaUsuarios.getTotalElements());
        data.put("totalPaginas", paginaUsuarios.getTotalPages());
        data.put("paginaActual", paginaUsuarios.getNumber());

        return new ApiResponseDTO(true, "Listado de usuarios", data, 200);
    }

    @Override
    public ApiResponseDTO actualizarUsuario(Integer id, UserDTO userDTO) {
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new ValidationException("No se encontró usuario con id: " + id);
        }

        User user = userOpt.get();

        if (userDTO.getUsernameUser() != null && !userDTO.getUsernameUser().trim().isEmpty()) {
            if (!user.getUsernameUser().equals(userDTO.getUsernameUser().trim()) &&
                    userRepository.existsByUsernameUser(userDTO.getUsernameUser().trim())) {
                throw new ValidationException("El username ya está en uso");
            }
            user.setUsernameUser(userDTO.getUsernameUser().trim());
        }

        if (userDTO.getEmailUser() != null && !userDTO.getEmailUser().trim().isEmpty()) {
            if (!userDTO.getEmailUser().trim().equals(user.getEmailUser()) &&
                    userRepository.existsByEmailUser(userDTO.getEmailUser().trim())) {
                throw new ValidationException("El email ya está registrado");
            }
            user.setEmailUser(userDTO.getEmailUser().trim());
        }

        if (userDTO.getDniUser() != null && !userDTO.getDniUser().trim().isEmpty()) {
            if (!userDTO.getDniUser().trim().equals(user.getDniUser())) {
                if (userDTO.getDniUser().trim().length() != 8) {
                    throw new ValidationException("El DNI debe tener exactamente 8 dígitos");
                }
                if (userRepository.existsByDniUser(userDTO.getDniUser().trim())) {
                    throw new ValidationException("El DNI ya está registrado");
                }
            }
            user.setDniUser(userDTO.getDniUser().trim());
        }

        if (userDTO.getNombreUser() != null) {
            user.setNombreUser(userDTO.getNombreUser());
        }

        if (userDTO.getApellidoUser() != null) {
            user.setApellidoUser(userDTO.getApellidoUser());
        }

        if (userDTO.getPasswordUser() != null && !userDTO.getPasswordUser().trim().isEmpty()) {
            user.setPasswordUser(userDTO.getPasswordUser());
        }

        if (userDTO.getIdRole() != null) {
            Optional<Role> roleOpt = roleRepository.findById(userDTO.getIdRole());
            if (!roleOpt.isPresent()) {
                throw new ValidationException("El rol especificado no existe");
            }
            Role role = roleOpt.get();
            if (role.getNameRole().equalsIgnoreCase("ADMINISTRADOR")) {
                throw new ValidationException("No está permitido asignar el rol ADMINISTRADOR");
            }
            user.setRole(role);
        }

        user.setUpdatedAt(LocalDateTime.now());
        User updated = userRepository.save(user);

        return new ApiResponseDTO(true, "Usuario actualizado correctamente", mapToResponse(updated), 200);
    }

    @Override
    public ApiResponseDTO toggleEstadoUsuario(Integer id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new ValidationException("No se encontró usuario con id: " + id);
        }

        User user = userOpt.get();
        boolean nuevoEstado = !user.getStatusUser();
        user.setStatusUser(nuevoEstado);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        String mensaje = nuevoEstado ? "Usuario activado correctamente" : "Usuario desactivado correctamente";
        return new ApiResponseDTO(true, mensaje, mapToResponse(user), 200);
    }

    @Override
    public ApiResponseDTO buscarPorUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("El username de búsqueda no puede estar vacío");
        }

        Optional<User> userOpt = userRepository.findByUsernameUser(username.trim());
        if (!userOpt.isPresent()) {
            throw new ValidationException("No se encontró usuario con username: " + username);
        }

        return new ApiResponseDTO(true, "Usuario encontrado", mapToResponse(userOpt.get()), 200);
    }

    @Override
    public ApiResponseDTO buscarPorDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            throw new ValidationException("El DNI de búsqueda no puede estar vacío");
        }

        Optional<User> userOpt = userRepository.findByDniUser(dni.trim());
        if (!userOpt.isPresent()) {
            throw new ValidationException("No se encontró usuario con DNI: " + dni);
        }

        return new ApiResponseDTO(true, "Usuario encontrado", mapToResponse(userOpt.get()), 200);
    }

    @Override
    public ApiResponseDTO filtrarUsuarios(String rol, Boolean estado, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<User> paginaUsuarios;

        if (rol != null && !rol.trim().isEmpty() && estado != null) {
            paginaUsuarios = userRepository.findByRole_NameRoleAndStatusUser(rol.trim().toUpperCase(), estado, pageable);
        } else if (rol != null && !rol.trim().isEmpty()) {
            paginaUsuarios = userRepository.findByRole_NameRole(rol.trim().toUpperCase(), pageable);
        } else if (estado != null) {
            paginaUsuarios = userRepository.findByStatusUser(estado, pageable);
        } else {
            paginaUsuarios = userRepository.findAll(pageable);
        }

        List<UserResponseDTO> lista = paginaUsuarios.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        Map<String, Object> data = new HashMap<>();
        data.put("usuarios", lista);
        data.put("totalElementos", paginaUsuarios.getTotalElements());
        data.put("totalPaginas", paginaUsuarios.getTotalPages());
        data.put("paginaActual", paginaUsuarios.getNumber());

        return new ApiResponseDTO(true, "Filtrado de usuarios", data, 200);
    }

    private UserResponseDTO mapToResponse(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setIdUser(user.getIdUser());
        dto.setUsernameUser(user.getUsernameUser());
        dto.setEmailUser(user.getEmailUser());
        dto.setDniUser(user.getDniUser());
        dto.setNombreUser(user.getNombreUser());
        dto.setApellidoUser(user.getApellidoUser());
        dto.setRol(user.getRole().getNameRole());
        dto.setStatusUser(user.getStatusUser());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
