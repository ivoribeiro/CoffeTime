import java.util.concurrent.Semaphore;

public class Main extends Thread implements Contract {

    //Class that handle the coffee machine states
    private States __states;
    //Thread of screen module
    private Screen __screen;
    //Thread of log module
    private Log __log;

    private Semaphore __startLogSemaphore;

    public Semaphore getLogSemaphore() {
        return this.__startLogSemaphore;
    }

    public static void main(String args[]) throws InterruptedException {
        Main main = new Main();
        main.start();
    }


    public void run() {
        //instanciar semaforos para controlar os tempos das execuções das threads
        this.__startLogSemaphore = new Semaphore(1);
        Semaphore __startScreenSemaphore = new Semaphore(1);
        //configs class
        Configs configs = new Configs();
        //states class
        this.__states = new States(configs);

        try {
            this.__startLogSemaphore.acquire();
            __startScreenSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.__log = new Log(configs.getLog());
        this.__log.start();
        //release semaphore , now all the threads can use logs
        this.__startLogSemaphore.release();

        //initialize threads
        this.__screen = new Screen(this, "Bebidas quentes aos melhores preços");
        this.__screen.start();
        //release semaphore , now all the threads can use screen
        __startScreenSemaphore.release();

        Keyboard __keyboard = new Keyboard(this);
        __keyboard.start();
        Slot __slot = new Slot(this);
        __slot.start();
    }


    /**
     * Method of contract interface to slot communicate with the main
     * adds the value inserted in slot to the slot state value
     *
     * @param money
     */
    public void processSlot(double money) {
        this.printScreenMessage("Processing " + money + " to slot ....", 1000);
        this.__states.addToSlot(money);
        this.printScreenMessage("Credit: " + this.formatDouble(this.__states.onSlot()), 1000);
    }

    /**
     * Method of contract interface to the keyboard communicate with the main
     * process the request of a drink
     *
     * @param drink
     */
    public void processDrink(String drink) {
        //if has enough money on slot
        if (this.hasMoney(drink)) {
            this.printScreenMessage("Processing " + drink + " ...", 1000);
            //decrement resources
            if (this.decrementResources(drink) != null) {
                //get the exchange value
                double exchange = this.exchange(drink);
                if (exchange != 0) this.printScreenMessage("Troco:" + this.formatDouble(exchange), 1000);
            }
            //reset the slot state value
            this.__states.resetSlot();
        } else
            this.printScreenMessage("Sem crédito suficiente, preço:" + this.formatDouble(this.__states.getProduct(drink).price()), 1000);
    }

    /**
     * @param logMessage
     */
    public void writeLogMessage(String logMessage) {
        this.__log.processLog(logMessage);

    }

    /**
     * @param message
     */
    public void printScreenMessage(String message, int time) {
        this.__screen.processMessage(message, time);
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
     * Gets the exchange value for the actual slot money and product price
     *
     * @param drink
     * @return
     */
    private Double exchange(String drink) {
        return this.__states.onSlot() - this.__states.getProduct(drink).price();
    }

    /**
     * @param drink
     * @return
     */
    private Boolean decrementResources(String drink) {
        Boolean soldout = false;
        String message = "";
        //decrement the drink
        if (this.__states.decrementProductQuantity(drink) == null) {
            message += this.__states.getProduct(drink).name() + ",";
            soldout = true;
        }
        if (this.__states.decrementProductQuantity("cup") == null) {
            message += this.__states.getProduct("cup").name() + ",";
            soldout = true;
        }
        if (this.__states.decrementProductQuantity("spoon") == null) {
            message += this.__states.getProduct("spoon").name() + ",";
            soldout = true;
        }
        if (this.__states.decrementProductQuantity("sugar") == null) {
            message += this.__states.getProduct("sugar").name() + ",";
            soldout = true;
        }
        if (soldout) {
            this.printScreenMessage(message + " ESGOTADO", 2000);
        }
        return soldout;

    }

    /**
     * @param number
     * @return
     */
    private String formatDouble(double number) {
        return String.format("%.2f", number);
    }

}
