package pe.edu.untels.dtos;

public class StockMinimoRequestDTO {

    private int stockMinimo;

    public StockMinimoRequestDTO() {}

    public StockMinimoRequestDTO(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }
}
