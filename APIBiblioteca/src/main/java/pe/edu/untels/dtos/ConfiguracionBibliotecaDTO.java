package pe.edu.untels.dtos;

public class ConfiguracionBibliotecaDTO {

    private int idConfiguracionBiblioteca;
    private int diasMaxPrestamo;
    private int limitePrestamos;
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
