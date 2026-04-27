package pe.edu.untels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.entities.Student;
import java.util.List;
import java.util.Optional;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByCodigoStudent(String codigoStudent);
    List<Student> findByStatusStudent(boolean statusStudent);
    boolean existsByCodigoStudent(String codigoStudent);
}

