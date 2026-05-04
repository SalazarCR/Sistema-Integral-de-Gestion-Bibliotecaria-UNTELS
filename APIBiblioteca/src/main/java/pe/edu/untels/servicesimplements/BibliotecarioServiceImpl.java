package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.entities.Bibliotecario;
import pe.edu.untels.repositories.IBibliotecarioRepository;
import pe.edu.untels.servicesinterfaces.IBibliotecarioService;

@Service
public class BibliotecarioServiceImpl implements IBibliotecarioService {

    @Autowired
    private IBibliotecarioRepository repository;

    @Override
    public Bibliotecario registrar(Bibliotecario b) {
        b.setActivo(true);
        return repository.save(b);
    }
}
