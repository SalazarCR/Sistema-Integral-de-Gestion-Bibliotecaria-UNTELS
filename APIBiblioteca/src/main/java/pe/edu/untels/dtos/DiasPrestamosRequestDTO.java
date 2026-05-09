package pe.edu.untels.dtos;

public class DiasPrestamosRequestDTO {

    private int diasPrestamo;

    public DiasPrestamosRequestDTO() {}

    public DiasPrestamosRequestDTO(int diasPrestamo) {
        this.diasPrestamo = diasPrestamo;
    }

    public int getDiasPrestamo() { return diasPrestamo; }
    public void setDiasPrestamo(int diasPrestamo) { this.diasPrestamo = diasPrestamo; }
}
