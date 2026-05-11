package pe.edu.untels.enums;

public enum TipoParametro {
    DIAS_PRESTAMO("Días de Préstamo", 1, 365),
    LIMITE_PRESTAMOS("Límite de Préstamos", 1, 100),
    MULTA_RETRASO("Multa por Retraso (Soles)", 0, 1000);

    private final String descripcion;
    private final int valorMinimo;
    private final int valorMaximo;

    TipoParametro(String descripcion, int valorMinimo, int valorMaximo) {
        this.descripcion = descripcion;
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getValorMinimo() {
        return valorMinimo;
    }

    public int getValorMaximo() {
        return valorMaximo;
    }

    public boolean validarValor(int valor) {
        return valor >= valorMinimo && valor <= valorMaximo;
    }
}

