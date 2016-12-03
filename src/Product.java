/**
 * Created by ivoribeiro on 17-11-2016.
 */
public class Product {

    private String __name;
    private double __price;
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


}
