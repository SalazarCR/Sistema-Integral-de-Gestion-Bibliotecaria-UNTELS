package pe.edu.untels.dtos;

public class LimitePrestamosRequestDTO {

    private int limitePrestamos;

    public LimitePrestamosRequestDTO() {}

    public LimitePrestamosRequestDTO(int limitePrestamos) {
        this.limitePrestamos = limitePrestamos;
    }

    public int getLimitePrestamos() { return limitePrestamos; }
    public void setLimitePrestamos(int limitePrestamos) { this.limitePrestamos = limitePrestamos; }
}
