package trader;

import java.io.Serializable;

public class Share implements Serializable {
	private static final long serialVersionUID = 1946167155981741066L;
	private Stock stock;
    private int quantity;

    // Constructors
    public Share(Stock stock, int quantity) {
        this.stock = stock;
        this.quantity = quantity;
    }

    public Share(Stock stock) {
        this(stock, 0);
    }

    public double calculateValue() {
        return quantity * stock.getPrice();
    }

    public int hashCode() {
        return stock.getSymbol().hashCode();
    }

    public boolean equals(Object anObject) {
        Share sr = (Share) anObject;
        return sr.stock.getSymbol().equals(this.stock.getSymbol()) && sr.quantity == this.quantity;
    }

    // Accessor methods
    public Stock getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }
    
    // Mutator methods
    public void setStock(Stock newStock) {
        stock = newStock;
    }

    public void setQuantity(int newQuantity) {
        quantity = newQuantity;
    }

    public String toString() {
        return "Share:  " + stock.getSymbol() + "  " + quantity;
    }
}
