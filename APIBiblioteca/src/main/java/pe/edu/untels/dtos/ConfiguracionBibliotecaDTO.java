package pe.edu.untels.dtos;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class ConfiguracionBibliotecaDTO {

    private int idConfiguracionBiblioteca;

    @Positive(message = "Los dias maximos de prestamo deben ser mayores a cero")
    private int diasMaxPrestamo;

    @Positive(message = "El limite de prestamos debe ser mayor a cero")
    private int limitePrestamos;

    @PositiveOrZero(message = "La multa por dia no puede ser negativa")
    private double multaPorDia;

    private boolean schedulerActivo;
    private boolean notifEmail;
    private boolean alertaStock;
    private boolean modoMant;

    public int getIdConfiguracionBiblioteca() {
        return idConfiguracionBiblioteca;
    }

    public void setIdConfiguracionBiblioteca(int idConfiguracionBiblioteca) {
        this.idConfiguracionBiblioteca = idConfiguracionBiblioteca;
    }

    public int getDiasMaxPrestamo() {
        return diasMaxPrestamo;
    }

    public void setDiasMaxPrestamo(int diasMaxPrestamo) {
        this.diasMaxPrestamo = diasMaxPrestamo;
    }

    public int getLimitePrestamos() {
        return limitePrestamos;
    }

    public void setLimitePrestamos(int limitePrestamos) {
        this.limitePrestamos = limitePrestamos;
    }

    public double getMultaPorDia() {
        return multaPorDia;
    }

    public void setMultaPorDia(double multaPorDia) {
        this.multaPorDia = multaPorDia;
    }

    public boolean isSchedulerActivo() {
        return schedulerActivo;
    }

    public void setSchedulerActivo(boolean schedulerActivo) {
        this.schedulerActivo = schedulerActivo;
    }

    public boolean isNotifEmail() {
        return notifEmail;
    }

    public void setNotifEmail(boolean notifEmail) {
        this.notifEmail = notifEmail;
    }

    public boolean isAlertaStock() {
        return alertaStock;
    }

    public void setAlertaStock(boolean alertaStock) {
        this.alertaStock = alertaStock;
    }

    public boolean isModoMant() {
        return modoMant;
    }

    public void setModoMant(boolean modoMant) {
        this.modoMant = modoMant;
    }
}
