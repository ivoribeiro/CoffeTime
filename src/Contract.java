/**
 * Created by ivoribeiro on 18-11-2016.
 */
public interface Contract {
    /**
     * Method to threads process a drink request from the keyboard
     *
     * @param drink
     */
    public void processDrink(String drink);

    /**
     * Method to threads Add money to slot state
     *
     * @param money
     */
    public void processSlot(double money);

    /**
     * Method to threads write on log
     *
     * @param logMessage
     */
    public void writeLogMessage(String logMessage);

}
