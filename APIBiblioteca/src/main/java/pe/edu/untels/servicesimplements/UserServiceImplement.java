@Service
public class UserServiceImplement implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        Optional<User> userOpt = Optional.empty();

        // buscar por username
        if (loginRequestDTO.getUsername() != null && !loginRequestDTO.getUsername().isBlank()) {
            userOpt = userRepository.findByUsernameUser(loginRequestDTO.getUsername());
        }

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Credenciales inválidas");
        }

        User user = userOpt.get();

        // 🔥 CORRECTO: usar PasswordEncoder
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPasswordUser())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // usuario activo
        if (!user.isStatusUser()) {
            throw new RuntimeException("Acceso restringido: usuario deshabilitado");
        }

        String roleName = (user.getRole() != null)
                ? user.getRole().getNameRole()
                : "SIN_ROL";

        return LoginResponseDTO.ok(
                user.getIdUser(),
                user.getUsernameUser(),
                user.getEmailUser(),
                roleName
        );
    }

    @Override
    public User createUser(User user) {

        if (userRepository.existsByUsernameUser(user.getUsernameUser())) {
            throw new RuntimeException("El usuario ya existe");
        }

        // 🔥 encriptar contraseña
        user.setPasswordUser(passwordEncoder.encode(user.getPasswordUser()));

        user.setStatusUser(true); // por defecto activo

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

    // 🔥 TOGGLE (YA ESTÁ BIEN PERO LO DEJAMOS LIMPIO)
    @Override
    public User toggleUserStatus(int idUser) {

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setStatusUser(!user.isStatusUser());

        return userRepository.save(user);
    }
}