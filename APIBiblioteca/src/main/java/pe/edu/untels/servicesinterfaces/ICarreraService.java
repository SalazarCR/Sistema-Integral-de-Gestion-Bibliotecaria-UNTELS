package pe.edu.untels.servicesinterfaces;

import pe.edu.untels.dtos.CarreraDTO;
import java.util.List;
import java.util.Optional;

public interface ICarreraService {
    CarreraDTO createCarrera(CarreraDTO carreraDTO);
    Optional<CarreraDTO> getCarreraById(int idCarrera);
    List<CarreraDTO> listCarreras();
    List<CarreraDTO> listActiveCarreras();
    CarreraDTO updateCarrera(int idCarrera, CarreraDTO carreraDTO);
    void deleteCarrera(int idCarrera);
}

