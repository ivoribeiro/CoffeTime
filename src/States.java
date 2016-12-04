import java.util.HashMap;

class States {

    private double __onSlot;
    private final int __onSugar;

    private HashMap<String, Product> products;


    public States(Configs configs) {

        this.__onSlot = 0;
        this.__onSugar = 0;

        this.products = new HashMap<>();

        //------------------------------------------------------------------Load default values from configs
        this.products.put("choco", new Product(configs.getChoco().name(), configs.getChoco().price(), configs.getChoco().quantity()));
        this.products.put("coffe", new Product(configs.getCoffe().name(), configs.getCoffe().price(), configs.getCoffe().quantity()));
        this.products.put("desca", new Product(configs.getDescafe().name(), configs.getDescafe().price(), configs.getDescafe().quantity()));
        this.products.put("tea", new Product(configs.getTea().name(), configs.getTea().price(), configs.getTea().quantity()));

        this.products.put("sugar", new Product(configs.getSugar().name(), configs.getSugar().price(), configs.getSugar().quantity()));
        this.products.put("spoon", new Product(configs.getSpoon().name(), configs.getSpoon().price(), configs.getSpoon().quantity()));
        this.products.put("cup", new Product(configs.getCup().name(), configs.getCup().price(), configs.getCup().quantity()));
        //--------------------------------------------------------------------------------------------------
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
        this.__onSlot += value;
        return this.__onSlot;
    }

    public void resetSlot() {
        this.__onSlot = 0;
    }


    public Product decrementProductQuantity(String product) {
        return this.getProduct(product).decrementQuantity();
    }
}
