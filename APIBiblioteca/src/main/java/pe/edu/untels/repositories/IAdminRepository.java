package pe.edu.untels.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.untels.entities.Admin;

import java.util.List;

public interface IAdminRepository extends JpaRepository<Admin, Long> {

    List<Admin> findByActivoTrue();
}