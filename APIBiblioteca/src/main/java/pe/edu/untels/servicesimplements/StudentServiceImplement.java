package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.dtos.StudentDTO;
import pe.edu.untels.entities.Carrera;
import pe.edu.untels.entities.Student;
import pe.edu.untels.repositories.CarreraRepository;
import pe.edu.untels.repositories.StudentRepository;
import pe.edu.untels.servicesinterfaces.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class StudentServiceImplement implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Override
    public Student registrar(StudentDTO dto) {

        if (studentRepository.findByDni(dto.getDni()).isPresent()) {
            throw new RuntimeException("DNI ya registrado");
        }

        if (studentRepository.findByCodigo(dto.getCodigo()).isPresent()) {
            throw new RuntimeException("Código ya registrado");
        }

        Carrera carrera = carreraRepository.findById(dto.getCarreraId())
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        Student student = new Student();

        student.setNombres(dto.getNombres());
        student.setApellidos(dto.getApellidos());
        student.setDni(dto.getDni());
        student.setCodigo(dto.getCodigo());
        student.setEmail(dto.getEmail());
        student.setEstado(true);
        student.setCarrera(carrera);

        return studentRepository.save(student);
    }

    @Override
    public Student actualizar(Long id, StudentDTO dto) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Carrera carrera = carreraRepository.findById(dto.getCarreraId())
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        student.setNombres(dto.getNombres());
        student.setApellidos(dto.getApellidos());
        student.setDni(dto.getDni());
        student.setCodigo(dto.getCodigo());
        student.setEmail(dto.getEmail());
        student.setCarrera(carrera);

        return studentRepository.save(student);
    }

    @Override
    public Student toggleEstado(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        student.setEstado(!student.getEstado());

        return studentRepository.save(student);
    }

    @Override
    public Student buscarPorDni(String dni) {
        return studentRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    @Override
    public Student buscarPorCodigo(String codigo) {
        return studentRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    @Override
    public List<Student> filtrarPorCarrera(Long carreraId) {
        return studentRepository.findByCarreraId(carreraId);
    }

    @Override
    public List<Student> filtrarPorEstado(Boolean estado) {
        return studentRepository.findByEstado(estado);
    }

    @Override
    public Page<Student> listar(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }
}
