import java.util.concurrent.Semaphore;

/**
 * Created by ivoribeiro on 17-11-2016.
 */
public class Main extends Thread implements Contract {

    //Class that handle the coffee machine states
    private States __states;
    //Thread of screen module
    private Screen __screen;
    //Thread of log module
    private Log __log;

    private Semaphore __logSemaphore;
    private Keyboard __keyboard;
    private Slot __slot;

    public Semaphore getLogSemaphore() {
        return this.__logSemaphore;
    }

    public static void main(String args[]) throws InterruptedException {


        Semaphore screenSemaphore = new Semaphore(1);
        Semaphore slotSemaphore = new Semaphore(1);
        Semaphore keyboardSemaphore = new Semaphore(1);
        Semaphore mainSemaphore = new Semaphore(1);


        Main main = new Main();
        main.start();



        System.out.println("Fim das threads");
    }


    public void run() {

        this.__logSemaphore = new Semaphore(1);
        try {
            this.__logSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Configs configs = new Configs();
        this.__states = new States(configs);

        this.__log = new Log(configs.getLog());
        this.__screen = new Screen(this);
        this.__keyboard = new Keyboard(this);
        this.__slot = new Slot(this);

        //start the threads
        this.__log.start();
        this.__logSemaphore.release();
        this.__screen.start();
        this.__slot.start();
        this.__keyboard.start();
    }


    /**
     * Mehtod of contract interface to slot communicate with the main
     * adds the value inserted in slot to the slot state value
     *
     * @param money
     */
    public void processSlot(double money) {
        this.__screen.processMessage("Processing " + money + " to slot ....");
        this.__states.addToSlot(money);
        this.__screen.processMessage("Credit: " + this.__states.onSlot());

    }

    /**
     * Method of contract interface to the keyboard communicate with the main
     * process the request of a drink
     *
     * @param drink
     */
    public void processDrink(String drink) {
        //if has enough money on slot
        // and if the machine has the resources to process the request
        if (this.hasMoney(drink) && this.hasResources(drink)) {
            this.__screen.processMessage("Processing " + drink + " ...");
            //get the exchange value
            double exchange = this.exchange(drink);
            if (exchange != 0) this.__screen.processMessage("Troco:" + exchange);

            //reset the slot state value
            this.__states.resetSlot();
        } else this.__screen.processMessage("Sem crédito suficiente, preço:" + this.__states.getProduct(drink).price());

    }

    public void writeLogMessage(String logMessage) {
        this.__log.processLog(logMessage);

    }

    /**
     * Validator method to check if the slot value is enough to process the request
     *
     * @param drink
     * @return
     */
    private boolean hasMoney(String drink) {
        return this.__states.onSlot() >= this.__states.getProduct(drink).price();
    }

    /**
     * Validator method to check if the machine as enough resources to process the request
     *
     * @param drink
     * @return
     */
    private boolean hasResources(String drink) {
        return this.__states.getCup().quantity() >= 1 && this.__states.getSpoon().quantity() >= 1 && this.__states.getProduct(drink).quantity() >= 1;
    }

    /**
     * Gets the exchange value for the actual slot money and product price
     *
     * @param drink
     * @return
     */
    public double exchange(String drink) {
        return this.__states.onSlot() - this.__states.getProduct(drink).price();
    }

}
