public class Product {

    private final String __name;
    private final double __price;
    private int __quantity;

    public Product(String name, double price, int quantity) {
        this.__name = name;
        this.__price = price;
        this.__quantity = quantity;
    }

    public double price() {
        return this.__price;
    }

    public int quantity() {
        return this.__quantity;
    }

    public String name() {
        return this.__name;
    }

    public Product decrementQuantity() {
        if (this.__quantity == 0) return null;
        else {
            this.__quantity--;
            return this;
        }
    }
}
