interface Contract {
    /**
     * Method to threads process a drink request from the keyboard
     *
     * @param drink
     */
     void processDrink(String drink);

    /**
     * Method to threads Add money to slot state
     *
     * @param money
     */
     void processSlot(double money);

    /**
     * Method to threads write on log
     *
     * @param logMessage
     */
     void writeLogMessage(String logMessage);

     void printScreenMessage(String message, int time);

}
