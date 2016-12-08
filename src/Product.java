import java.util.concurrent.Semaphore;

public class Product {

    private final String __name;
    private final double __price;
    private int __quantity;
    private int __grain;
    private Semaphore productSemaphore;

    public Product(String name, double price, int quantity, int grain) {
        this.__name = name;
        this.__price = price;
        this.__quantity = quantity;
        this.__grain = grain;
        this.productSemaphore = new Semaphore(1);
    }

    public int getGrain() {
        return this.__grain;
    }

    public void setGrain(int grain) {
        this.__grain = grain;
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
            try {
                this.productSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.__quantity--;
            this.productSemaphore.release();
            return this;
        }
    }

    public Product decrementGrain() {
        if (this.__grain == 0) return null;
        else {
            try {
                this.productSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.__grain--;
            this.productSemaphore.release();
            return this;
        }
    }

    public boolean hasQuantity() {
        return this.__quantity > 0;
    }

    public boolean hasGrain() {
        return this.__grain > 0;
    }

    public void setQuantity(int quantity) {
        this.__quantity = quantity;
    }
}
