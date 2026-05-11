package pe.edu.untels.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.untels.entities.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Page<Student> findAll(Pageable pageable);

    Optional<Student> findByDni(String dni);

    Optional<Student> findByCodigo(String codigo);

    List<Student> findByCarreraId(Long carreraId);

    List<Student> findByEstado(Boolean estado);
}