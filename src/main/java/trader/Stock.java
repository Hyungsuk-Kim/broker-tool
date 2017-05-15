package trader;

import java.io.Serializable;

public class Stock implements Serializable {
	private static final long serialVersionUID = 1392868822792320158L;
	private String symbol;
    private float price;

    // Constructors
    public Stock(String symbol, float price) {
        this.symbol = symbol;
        this.price = price;
    }

    // Accessor methods
    public float getPrice() {
        return price;
    }

    public String getSymbol() {
        return symbol;
    }

    // Mutator methods
    public void setPrice(float newPrice) {
        price = newPrice;
    }

    public String toString() {
        return "Stock:  " + symbol + "  " + price;
    }
}