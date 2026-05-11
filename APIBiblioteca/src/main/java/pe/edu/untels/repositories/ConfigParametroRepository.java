package pe.edu.untels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.untels.entities.ConfigParametro;
import pe.edu.untels.enums.TipoParametro;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfigParametroRepository extends JpaRepository<ConfigParametro, Long> {

    Optional<ConfigParametro> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    List<ConfigParametro> findByTipo(TipoParametro tipo);

    void deleteByNombre(String nombre);
}

