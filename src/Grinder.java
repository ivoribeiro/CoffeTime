public class Grinder extends Thread {

    Main __main;
    String __drink;


    public Grinder(Main main, String drink) {
        this.__main = main;
        this.__drink = drink;
    }

    /**
     * Wake up the current thread
     */
    public synchronized void wakeUp() {
        __main.writeLogMessage("[Grinder]-awake order received");
        this.notify();
        __main.writeLogMessage("[Grinder]-thread awaked");

    }

    /**
     * Sleep the current thread
     */
    public synchronized void sleep() {
        __main.writeLogMessage("[Grinder]-sleeping order received");
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            this.__main.getGrinderSemaphore().acquire();
            this.__main.printScreenMessage("Graining " + this.__drink, 1000);
            String str = "";
            for (int i = 0; i <= 9; i++) {
                str = str + ".";
                this.__main.printScreenMessage(str, 500);
            }
            this.__main.printScreenMessage("Grindered", 1000);
            this.__main.getGrinderSemaphore().release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
