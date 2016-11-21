/**
 * Created by ivoribeiro on 17-11-2016.
 */
public class States {

    private double __onSlot;
    private int __onSugar;

    private Product __sugar;
    private Product __cup;
    private Product __spoon;

    private Product __coffe;
    private Product __choco;
    private Product __decaffeinated;
    private Product __tea;


    public States(Configs configs) {

        this.__onSlot = 0;
        this.__onSugar = 0;

        //------------------------------------------------------------------Load default values from configs
        this.__choco = new Product(configs.getChoco().price(), configs.getChoco().quantity());
        this.__coffe = new Product(configs.getCoffe().price(), configs.getCoffe().quantity());
        this.__decaffeinated = new Product(configs.getDescafe().price(), configs.getDescafe().quantity());
        this.__tea = new Product(configs.getTea().price(), configs.getTea().quantity());

        this.__sugar = new Product(configs.getSugar().price(), configs.getSugar().quantity());
        this.__spoon = new Product(configs.getSpoon().price(), configs.getSpoon().quantity());
        this.__cup = new Product(configs.getCup().price(), configs.getCup().quantity());
        //--------------------------------------------------------------------------------------------------
    }

    /**
     * Get a product from a string
     *
     * @param product
     * @return
     */
    public Product getProduct(String product) {
        switch (product) {
            case "coffe":
                return this.__coffe;
            case "choco":
                return this.__choco;
            case "decaffeinated":
                return this.__decaffeinated;
            case "tea":
                return this.__tea;
            default:
                return null;
        }
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


    public Product getCup() {
        return this.__cup;
    }

    public Product getSpoon() {
        return this.__spoon;
    }
}
