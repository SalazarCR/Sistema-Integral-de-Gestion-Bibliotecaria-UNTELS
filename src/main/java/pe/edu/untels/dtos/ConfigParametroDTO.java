package pe.edu.untels.dtos;

import java.time.LocalDateTime;

public class ConfigParametroDTO {

    private int idConfig;
    private int limitePrestamos;
    private int diasPrestamo;
    private int stockMinimo;
    private LocalDateTime fechaActualizacion;

    public ConfigParametroDTO() {}

    public ConfigParametroDTO(int idConfig, int limitePrestamos, int diasPrestamo, int stockMinimo, LocalDateTime fechaActualizacion) {
        this.idConfig = idConfig;
        this.limitePrestamos = limitePrestamos;
        this.diasPrestamo = diasPrestamo;
        this.stockMinimo = stockMinimo;
        this.fechaActualizacion = fechaActualizacion;
    }

    public int getIdConfig() { return idConfig; }
    public void setIdConfig(int idConfig) { this.idConfig = idConfig; }

    public int getLimitePrestamos() { return limitePrestamos; }
    public void setLimitePrestamos(int limitePrestamos) { this.limitePrestamos = limitePrestamos; }

    public int getDiasPrestamo() { return diasPrestamo; }
    public void setDiasPrestamo(int diasPrestamo) { this.diasPrestamo = diasPrestamo; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
