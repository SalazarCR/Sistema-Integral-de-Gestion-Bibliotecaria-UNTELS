package pe.edu.untels.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Table(name = "ConfigParametro")
public class ConfigParametro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConfig;

    @Column(name = "limitePrestamos", nullable = false)
    @Min(1)
    private int limitePrestamos;

    @Column(name = "diasPrestamo", nullable = false)
    @Min(1)
    private int diasPrestamo;

    @Column(name = "stockMinimo", nullable = false)
    @Min(0)
    private int stockMinimo;

    @Column(name = "fechaActualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    public ConfigParametro() {}

    public ConfigParametro(int limitePrestamos, int diasPrestamo, int stockMinimo) {
        this.limitePrestamos = limitePrestamos;
        this.diasPrestamo = diasPrestamo;
        this.stockMinimo = stockMinimo;
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
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