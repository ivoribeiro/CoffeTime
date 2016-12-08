import java.util.HashMap;
import java.util.concurrent.Semaphore;

class States {

    private double __onSlot;
    private final int __onSugar;
    private Semaphore statesSemaphore;

    private HashMap<String, Product> products;


    public States(Configs configs) {

        this.__onSlot = 0;
        this.__onSugar = 0;

        this.products = new HashMap<>();

        this.statesSemaphore = new Semaphore(1);
        try {
            this.statesSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //------------------------------------------------------------------Load default values from configs
        this.products.put("choco", new Product(configs.getChoco().name(), configs.getChoco().price(), configs.getChoco().quantity(), 0));
        this.products.put("coffe", new Product(configs.getCoffe().name(), configs.getCoffe().price(), configs.getCoffe().quantity(), configs.getCoffe().getGrain()));
        this.products.put("desca", new Product(configs.getDescafe().name(), configs.getDescafe().price(), configs.getDescafe().quantity(), configs.getCoffe().getGrain()));
        this.products.put("tea", new Product(configs.getTea().name(), configs.getTea().price(), configs.getTea().quantity(), 0));

        this.products.put("sugar", new Product(configs.getSugar().name(), configs.getSugar().price(), configs.getSugar().quantity(), 0));
        this.products.put("spoon", new Product(configs.getSpoon().name(), configs.getSpoon().price(), configs.getSpoon().quantity(), 0));
        this.products.put("cup", new Product(configs.getCup().name(), configs.getCup().price(), configs.getCup().quantity(), 0));
        //--------------------------------------------------------------------------------------------------

        this.statesSemaphore.release();
    }

    /**
     * Get a product from a string
     *
     * @param product
     * @return
     */
    public Product getProduct(String product) {
        return this.products.get(product);
    }

    public double onSlot() {
        return this.__onSlot;
    }

    public int onSugar() {
        return this.__onSugar;
    }

    public double addToSlot(double value) {
        try {
            this.statesSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.__onSlot += value;
        this.statesSemaphore.release();
        return this.__onSlot;
    }

    public void resetSlot() {
        try {
            this.statesSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.__onSlot = 0;
        this.statesSemaphore.release();
    }


    public Product decrementProductQuantity(String product) {
        return this.getProduct(product).decrementQuantity();
    }

    public Product decrementGrain(String product) {
        return this.getProduct(product).decrementGrain();
    }
}
