package pe.edu.untels.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "sanciones")
public class Sancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSancion;

    @ManyToOne
    @JoinColumn(name = "idEstudiante", nullable = false)
    private Usuario estudiante;

    @Column(name = "motivo", length = 200)
    private String motivo;

    @Column(name = "diasSuspension")
    private int diasSuspension;

    @Column(name = "multa")
    private double multa;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "fechaCreacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fechaFin")
    private LocalDateTime fechaFin;

    public Sancion() {
    }

    public int getIdSancion() {
        return idSancion;
    }

    public void setIdSancion(int idSancion) {
        this.idSancion = idSancion;
    }

    public Usuario getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Usuario estudiante) {
        this.estudiante = estudiante;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getDiasSuspension() {
        return diasSuspension;
    }

    public void setDiasSuspension(int diasSuspension) {
        this.diasSuspension = diasSuspension;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
}
