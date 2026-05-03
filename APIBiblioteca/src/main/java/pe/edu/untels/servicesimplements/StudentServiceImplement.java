package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.untels.dtos.StudentDTO;
import pe.edu.untels.dtos.StudentRegisterDTO;
import pe.edu.untels.entities.Role;
import pe.edu.untels.entities.Student;
import pe.edu.untels.entities.User;
import pe.edu.untels.repositories.IRoleRepository;
import pe.edu.untels.repositories.IStudentRepository;
import pe.edu.untels.repositories.IUserRepository;
import pe.edu.untels.servicesinterfaces.IStudentService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImplement implements IStudentService {
    @Autowired
    private IStudentRepository studentRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private StudentDTO mapToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setIdStudent(student.getIdStudent());
        dto.setCodigoStudent(student.getCodigoStudent());
        dto.setNameStudent(student.getNameStudent());
        dto.setEmailStudent(student.getEmailStudent());
        dto.setPhoneStudent(student.getPhoneStudent());
        dto.setCarreraStudent(student.getCarreraStudent());
        dto.setStatusStudent(student.isStatusStudent());
        return dto;
    }

    @Override
    public StudentDTO registerStudent(StudentRegisterDTO studentRegisterDTO) {
        if (studentRepository.existsByCodigoStudent(studentRegisterDTO.getCodigoStudent())) {
            throw new RuntimeException("El código de estudiante ya existe");
        }
        if (userRepository.existsByUsernameUser(studentRegisterDTO.getUsernameUser())) {
            throw new RuntimeException("El usuario ya existe");
        }

        Role roleEstudiante = roleRepository.findByNameRole("Estudiante").orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        User newUser = new User();
        newUser.setUsernameUser(studentRegisterDTO.getUsernameUser());
        newUser.setPasswordUser(passwordEncoder.encode(studentRegisterDTO.getPasswordUser()));
        newUser.setEmailUser(studentRegisterDTO.getEmailStudent());
        newUser.setStatusUser(true);
        newUser.setDateRegisterUser(LocalDateTime.now());
        newUser.setRole(roleEstudiante);
        User savedUser = userRepository.save(newUser);

        Student newStudent = new Student();
        newStudent.setCodigoStudent(studentRegisterDTO.getCodigoStudent());
        newStudent.setNameStudent(studentRegisterDTO.getNameStudent());
        newStudent.setEmailStudent(studentRegisterDTO.getEmailStudent());
        newStudent.setPhoneStudent(studentRegisterDTO.getPhoneStudent());
        newStudent.setCarreraStudent(studentRegisterDTO.getCarreraStudent());
        newStudent.setStatusStudent(true);
        newStudent.setDateRegisterStudent(LocalDate.now());
        newStudent.setUser(savedUser);

        Student savedStudent = studentRepository.save(newStudent);
        return mapToDTO(savedStudent);
    }

    @Override
    public List<StudentDTO> listStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<StudentDTO> listActiveStudents() {
        List<Student> students = studentRepository.findByStatusStudent(true);
        return students.stream().map(this::mapToDTO).toList();
    }

    @Override
    public Optional<StudentDTO> getStudentById(int idStudent) {
        Optional<Student> student = studentRepository.findById(idStudent);
        return student.map(this::mapToDTO);
    }

    @Override
    public Optional<StudentDTO> getStudentByCodigo(String codigoStudent) {
        Optional<Student> student = studentRepository.findByCodigoStudent(codigoStudent);
        return student.map(this::mapToDTO);
    }

    @Override
    public StudentDTO updateStudent(int idStudent, StudentDTO studentDTO) {
        Optional<Student> studentOpt = studentRepository.findById(idStudent);
        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Estudiante no encontrado");
        }
        
        Student student = studentOpt.get();
        student.setNameStudent(studentDTO.getNameStudent());
        student.setEmailStudent(studentDTO.getEmailStudent());
        student.setPhoneStudent(studentDTO.getPhoneStudent());
        student.setCarreraStudent(studentDTO.getCarreraStudent());
        student.setStatusStudent(studentDTO.isStatusStudent());
        
        Student updatedStudent = studentRepository.save(student);
        return mapToDTO(updatedStudent);
    }

    @Override
    public void deleteStudent(int idStudent) {
        studentRepository.deleteById(idStudent);
    }
}







