import java.util.concurrent.Semaphore;

public class Main extends Thread implements Contract {

    //Class that handle the coffee machine states
    private States __states;
    //Thread of screen module
    private Screen __screen;
    //Thread of log module
    private Log __log;

    private Slot __slot;

    private Semaphore __startLogSemaphore;
    private Semaphore __grinderSemaphore;

    public Semaphore getLogSemaphore() {
        return this.__startLogSemaphore;
    }

    public static void main(String args[]) throws InterruptedException {
        Main main = new Main();
        main.start();
    }

    public Semaphore getGrinderSemaphore() {
        return this.__grinderSemaphore;
    }


    public void run() {
        //instanciar semaforos para controlar os tempos das execuções das threads
        this.__startLogSemaphore = new Semaphore(1);
        Semaphore __startScreenSemaphore = new Semaphore(1);
        this.__grinderSemaphore = new Semaphore(1);

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
        this.writeLogMessage("[Main]-Log thread started");
        //release semaphore , now all the threads can use logs
        this.__startLogSemaphore.release();
        this.writeLogMessage("[Main]-Log start semaphore released");

        //initialize threads
        this.writeLogMessage("[Main]-New instance of screen");
        this.__screen = new Screen(this, "Bebidas quentes aos melhores preços");
        this.writeLogMessage("[Main]-Starting screen thread");
        this.__screen.start();
        this.writeLogMessage("[Main]-Screen thread started");
        //release semaphore , now all the threads can use screen
        __startScreenSemaphore.release();
        this.writeLogMessage("[Main]-Screen semaphore released");
        this.writeLogMessage("[Main]-New instance of keyboard");
        Keyboard __keyboard = new Keyboard(this);
        this.writeLogMessage("[Main]-Starting keyboard thread");
        __keyboard.start();
        this.writeLogMessage("[Main]-Keyboard thread started");
        this.writeLogMessage("[Main]-New instance of Slot");
        this.__slot = new Slot(this);
        this.writeLogMessage("[Main]-Starting slot thread");
        __slot.start();
        this.writeLogMessage("[Main]-Slot thread started");
    }


    /**
     * Method of contract interface to slot communicate with the main
     * adds the value inserted in slot to the slot state value
     *
     * @param money
     */
    public void processSlot(double money) {
        this.printScreenMessage("Processing " + money + "€ to slot ....", 1000);
        this.writeLogMessage("[Main]-Adding money to states slot");
        this.__states.addToSlot(money);
        this.writeLogMessage("[Main]-Money added to states slot");
        this.printScreenMessage("Credit: " + this.formatDouble(this.__states.onSlot()), 1000);
    }

    /**
     * Method of contract interface to the keyboard communicate with the main
     * process the request of a drink
     *
     * @param drink
     */
    public void processDrink(String drink) {
        this.writeLogMessage("[Main]-Processing drink method");
        //if has enough money on slot
        this.writeLogMessage("[Main]-Has enought money on slot?");
        if (this.hasMoney(drink)) {
            this.writeLogMessage("[Main]-Yes it has enought money on slot");
            this.writeLogMessage("[Main]-processing drink");
            this.printScreenMessage("Processing " + drink + " ...", 1000);
            //decrement resources
            this.writeLogMessage("[Main]-can decrement resources?");
            if (this.decrementResources(drink) != null) {
                this.writeLogMessage("[Main]-resources decremented");
                //get the exchange value
                this.writeLogMessage("[Main]-calculating exchange");
                try {
                    this.__grinderSemaphore.acquire();
                    double exchange = this.exchange(drink);
                    if (exchange != 0) {
                        this.writeLogMessage("[Main]-Sending order to slot give the exchange");
                        this.__slot.giveExchange(this.formatDouble(exchange));
                        this.writeLogMessage("[Main]-Slot Exchange Order executed");
                    }
                    this.__grinderSemaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //reset the slot state value
            this.writeLogMessage("[Main]-Sending order to states reset slot to 0");
            this.__states.resetSlot();
            this.writeLogMessage("[Main]-States reset slot order executed");
        } else {
            this.writeLogMessage("[Main]-No sufficient credits to process drink");
            this.printScreenMessage("Sem crédito suficiente, preço:" + this.formatDouble(this.__states.getProduct(drink).price()) + "€", 1000);
        }
    }

    /**
     * @param logMessage
     */
    public void writeLogMessage(String logMessage) {
        //this.__log.processLog("[Main]-Give to [LOG] a message to process");
        this.__log.processLog(logMessage);
        //this.__log.processLog("[Main]-[LOG] message process order done");

    }

    /**
     * @param message
     */
    public void printScreenMessage(String message, int time) {
        //this.writeLogMessage("[Main]-Give to [SCREEN] a message to process");
        this.__screen.processMessage(message, time);
        //this.writeLogMessage("[Main]-[SCREEN] message process order done");
    }

    @Override
    public void resetSlot() {
        this.writeLogMessage("[Main]-Reset slot function");
        this.writeLogMessage("[Main]-Is the slot bigger than 0?");
        if (this.__states.onSlot() > 0) {
            this.writeLogMessage("[Main]-The slot is bigger than 0");
            this.writeLogMessage("[Main]-Giving back people money");
            this.printScreenMessage("A devolver" + this.__states.onSlot() + "€", 1000);
            this.writeLogMessage("[Main]-Money was gived back");
            this.writeLogMessage("[Main]-Sending order to states reset slot to 0");
            this.__states.resetSlot();
            this.writeLogMessage("[Main]-States reset slot order executed");
        }
    }


    /**
     * Validator method to check if the slot value is enough to process the request
     *
     * @param drink
     * @return
     */
    private boolean hasMoney(String drink) {
        this.writeLogMessage("[Main]-hasMoney method");
        this.writeLogMessage("[Main]-returning if the slot has enough money to the drink");
        return this.__states.onSlot() >= this.__states.getProduct(drink).price();
    }


    /**
     * Gets the exchange value for the actual slot money and product price
     *
     * @param drink
     * @return
     */
    private Double exchange(String drink) {
        this.writeLogMessage("[Main]-exchange method");
        this.writeLogMessage("[Main]-returning the exchange value");
        return this.__states.onSlot() - this.__states.getProduct(drink).price();
    }

    /**
     * @param drink
     * @return
     */
    private Boolean decrementResources(String drink) {
        this.writeLogMessage("[Main]-decrementResources function");
        Boolean soldout = false;
        String message = "";
        Product product = this.__states.getProduct(drink);
        //if quantity is 0
        if (product.hasQuantity() == false) {
            //if the product is a coffe or descafe and has no grain
            if (product.name().equals("coffe") && product.hasGrain() == false || product.name().equals("desca") && product.hasGrain() == false) {
                this.writeLogMessage("[Main]-add drink to sold out message");
                message += product.name() + ",";
                soldout = true;

            }// else if is a not grained product
            else if (!product.name().equals("coffe") && !product.name().equals("desca")) {
                this.writeLogMessage("[Main]-add drink to sold out message");
                message += product.name() + ",";
                soldout = true;
            }
        }
        if (this.__states.getProduct("cup").hasQuantity() == false) {
            this.writeLogMessage("[Main]-add cup to sold out message");
            message += this.__states.getProduct("cup").name() + ",";
            soldout = true;
        }
        if (this.__states.getProduct("spoon").hasQuantity() == false) {
            this.writeLogMessage("[Main]-add spoon to sold out message");
            message += this.__states.getProduct("spoon").name() + ",";
            soldout = true;
        }
        if (this.__states.getProduct("sugar").hasQuantity() == false) {
            this.writeLogMessage("[Main]-add sugar to sold out message");
            message += this.__states.getProduct("sugar").name() + ",";
            soldout = true;
        }
        if (soldout) {
            this.writeLogMessage("[Main]-Sending [Screen] a orderd to print the sold out message");
            this.printScreenMessage(message + " ESGOTADO", 2000);
            this.writeLogMessage("[Main]- [Screen] order to print sold out message executed");
        }
        //if any of the product is not sold out , decrement quantity
        else {
            this.__states.getProduct("sugar").decrementQuantity();
            this.__states.getProduct("cup").decrementQuantity();
            this.__states.getProduct("spoon").decrementQuantity();
            if (product.hasQuantity()) product.decrementQuantity();
            else {
                Grinder grinder = new Grinder(this, drink);
                grinder.start();
                product.setQuantity(10);
                product.decrementGrain();
            }

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
