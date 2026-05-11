package pe.edu.untels.servicesinterfaces;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.edu.untels.dtos.StudentDTO;
import pe.edu.untels.entities.Student;

import java.util.List;

public interface StudentService {

    Student registrar(StudentDTO dto);

    Student toggleEstado(Long id);

    Student buscarPorDni(String dni);

    Student buscarPorCodigo(String codigo);

    List<Student> filtrarPorCarrera(Long carreraId);

    List<Student> filtrarPorEstado(Boolean estado);

    Page<Student> listar(Pageable pageable);

    Student actualizar(Long id, StudentDTO dto);
}
