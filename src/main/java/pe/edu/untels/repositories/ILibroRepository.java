package pe.edu.untels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.entities.Libro;

@Repository
public interface ILibroRepository extends JpaRepository<Libro, Integer> {

}