package pe.edu.untels.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class PrestamoDTO {

    private int idPrestamo;

    @Positive(message = "Debe indicar un libro valido")
    private int idLibro;

    @Positive(message = "Debe indicar un estudiante valido")
    private int idEstudiante;

    private LocalDateTime fecha;
    private LocalDateTime fechaRecojo;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaConfirmacion;
    private LocalDateTime fechaDevolucion;
    private String estado;

    @NotBlank(message = "El motivo del prestamo es obligatorio")
    @Size(max = 255, message = "El motivo no puede superar los 255 caracteres")
    private String motivo;

    @Size(max = 100, message = "El curso no puede superar los 100 caracteres")
    private String curso;

    @Size(max = 500, message = "Las observaciones no pueden superar los 500 caracteres")
    private String observaciones;

    @Size(max = 30, message = "El estado de devolucion no puede superar los 30 caracteres")
    private String estadoDevolucion;

    @Size(max = 500, message = "Las observaciones de devolucion no pueden superar los 500 caracteres")
    private String observacionesDev;

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
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
