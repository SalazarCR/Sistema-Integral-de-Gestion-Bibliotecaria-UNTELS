package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.dtos.StudentDTO;
import pe.edu.untels.dtos.StudentRegisterDTO;
import pe.edu.untels.entities.Student;
import java.util.List;
import java.util.Optional;

public interface IStudentService {
    StudentDTO registerStudent(StudentRegisterDTO studentRegisterDTO);
    List<StudentDTO> listStudents();
    List<StudentDTO> listActiveStudents();
    Optional<StudentDTO> getStudentById(int idStudent);
    Optional<StudentDTO> getStudentByCodigo(String codigoStudent);
    Student updateStudent(Student student);
    void deleteStudent(int idStudent);
}

