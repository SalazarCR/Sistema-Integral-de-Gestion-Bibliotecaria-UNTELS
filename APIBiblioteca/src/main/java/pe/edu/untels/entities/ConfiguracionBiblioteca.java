package pe.edu.untels.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "configuracion_biblioteca")
public class ConfiguracionBiblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConfiguracionBiblioteca;

    @Column(name = "diasMaxPrestamo", nullable = false)
    private int diasMaxPrestamo;

    @Column(name = "limitePrestamos", nullable = false)
    private int limitePrestamos;

    @Column(name = "multaPorDia", nullable = false)
    private double multaPorDia;

    @Column(name = "schedulerActivo")
    private boolean schedulerActivo;

    @Column(name = "notifEmail")
    private boolean notifEmail;

    @Column(name = "alertaStock")
    private boolean alertaStock;

    @Column(name = "modoMant")
    private boolean modoMant;

    public ConfiguracionBiblioteca() {
    }

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
