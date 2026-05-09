package pe.edu.untels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.untels.entities.ConfigParametro;

public interface IConfigParametroRepository extends JpaRepository<ConfigParametro, Integer> {
}
