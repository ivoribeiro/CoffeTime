/**
 * Created by ivoribeiro on 17-11-2016.
 */
public class Product {

    private double __price;
    private int __quantity;

    public Product(double price, int quantity) {
        this.__price = price;
        this.__quantity = quantity;
    }

    public double price() {
        return this.__price;
    }

    public int quantity() {
        return this.__quantity;
    }


}
