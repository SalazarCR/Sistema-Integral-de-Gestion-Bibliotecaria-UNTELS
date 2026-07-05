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
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPrestamo;

    @ManyToOne
    @JoinColumn(name = "idLibro", nullable = false)
    private Libro libro;

    @ManyToOne
    @JoinColumn(name = "idEstudiante", nullable = false)
    private Usuario estudiante;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "fechaRecojo")
    private LocalDateTime fechaRecojo;

    @Column(name = "fechaEntrega", nullable = false)
    private LocalDateTime fechaEntrega;

    @Column(name = "fechaConfirmacion")
    private LocalDateTime fechaConfirmacion;

    @Column(name = "fechaDevolucion")
    private LocalDateTime fechaDevolucion;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "motivo", length = 255)
    private String motivo;

    @Column(name = "curso", length = 100)
    private String curso;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @Column(name = "estadoDevolucion", length = 30)
    private String estadoDevolucion;

    @Column(name = "observacionesDev", length = 500)
    private String observacionesDev;

    public Prestamo() {
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Usuario getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Usuario estudiante) {
        this.estudiante = estudiante;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public LocalDateTime getFechaRecojo() {
        return fechaRecojo;
    }

    public void setFechaRecojo(LocalDateTime fechaRecojo) {
        this.fechaRecojo = fechaRecojo;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public LocalDateTime getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public void setFechaConfirmacion(LocalDateTime fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    public LocalDateTime getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDateTime fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstadoDevolucion() {
        return estadoDevolucion;
    }

    public void setEstadoDevolucion(String estadoDevolucion) {
        this.estadoDevolucion = estadoDevolucion;
    }

    public String getObservacionesDev() {
        return observacionesDev;
    }

    public void setObservacionesDev(String observacionesDev) {
        this.observacionesDev = observacionesDev;
    }
}
