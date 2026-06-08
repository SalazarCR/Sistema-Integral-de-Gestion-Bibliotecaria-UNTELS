package pe.edu.untels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.entities.ConfiguracionBiblioteca;

@Repository
public interface IConfiguracionBibliotecaRepository extends JpaRepository<ConfiguracionBiblioteca, Integer> {
}
