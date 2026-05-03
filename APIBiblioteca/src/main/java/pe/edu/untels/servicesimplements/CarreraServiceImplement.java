package pe.edu.untels.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.untels.dtos.CarreraDTO;
import pe.edu.untels.entities.Carrera;
import pe.edu.untels.repositories.ICarreraRepository;
import pe.edu.untels.servicesinterfaces.ICarreraService;
import java.util.List;
import java.util.Optional;

@Service
public class CarreraServiceImplement implements ICarreraService {
    @Autowired
    private ICarreraRepository carreraRepository;

    private CarreraDTO mapToDTO(Carrera carrera) {
        return new CarreraDTO(carrera.getIdCarrera(), carrera.getNameCarrera(),
                              carrera.getDescriptionCarrera(), carrera.isStatusCarrera());
    }

    @Override
    public CarreraDTO createCarrera(CarreraDTO carreraDTO) {
        if (carreraRepository.existsByNameCarrera(carreraDTO.getNameCarrera())) {
            throw new RuntimeException("La carrera ya existe");
        }
        Carrera carrera = new Carrera(carreraDTO.getNameCarrera(),
                                      carreraDTO.getDescriptionCarrera(),
                                      carreraDTO.isStatusCarrera());
        Carrera savedCarrera = carreraRepository.save(carrera);
        return mapToDTO(savedCarrera);
    }

    @Override
    public Optional<CarreraDTO> getCarreraById(int idCarrera) {
        Optional<Carrera> carrera = carreraRepository.findById(idCarrera);
        return carrera.map(this::mapToDTO);
    }

    @Override
    public List<CarreraDTO> listCarreras() {
        List<Carrera> carreras = carreraRepository.findAll();
        return carreras.stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<CarreraDTO> listActiveCarreras() {
        return listCarreras().stream().filter(CarreraDTO::isStatusCarrera).toList();
    }

    @Override
    public CarreraDTO updateCarrera(int idCarrera, CarreraDTO carreraDTO) {
        Carrera carrera = carreraRepository.findById(idCarrera)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
        carrera.setNameCarrera(carreraDTO.getNameCarrera());
        carrera.setDescriptionCarrera(carreraDTO.getDescriptionCarrera());
        carrera.setStatusCarrera(carreraDTO.isStatusCarrera());
        Carrera updatedCarrera = carreraRepository.save(carrera);
        return mapToDTO(updatedCarrera);
    }

    @Override
    public void deleteCarrera(int idCarrera) {
        carreraRepository.deleteById(idCarrera);
    }
}

