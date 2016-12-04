class ScreenMessage {

    private final String message;
    private final int time;

    public ScreenMessage(String message, int time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public int getTime() {
        return time;
    }
}
