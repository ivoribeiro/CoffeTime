import javax.swing.*;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

class Screen extends Thread {

    private final Queue<ScreenMessage> __messageQueue;
    private final Main __main;
    private final String DEFAULT;

    /**
     * Wake up the current thread
     */
    public synchronized void wakeUp() {
        this.notify();
    }

    /**
     * Sleep the current thread
     */
    public synchronized void sleep() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param main
     * @param defaultMessage
     */
    public Screen(Main main, String defaultMessage) {
        this.__main = main;
        this.__messageQueue = new LinkedBlockingQueue<>();
        this.DEFAULT = defaultMessage;
    }

    /**
     * @param message
     * @param time
     */
    public void processMessage(String message, int time) {
        __messageQueue.offer(new ScreenMessage(message, time));
        this.wakeUp();
    }

    /**
     * @param screenLabel
     */
    public void printMessages(JLabel screenLabel) {
        while (!__messageQueue.isEmpty()) {
            ScreenMessage sm = this.__messageQueue.poll();
            screenLabel.setText(sm.getMessage());
            try {
                Thread.sleep(sm.getTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        screenLabel.setText(this.DEFAULT);
        this.sleep();
        this.printMessages(screenLabel);
    }

    /**
     * Build the frame UI
     *
     * @return
     */
    private JLabel buildUI() {

        String myname = Thread.currentThread().getName();
        LayoutBuilder layoutBuilder = new LayoutBuilder(myname, 500, 75);
        layoutBuilder.setPosition(450, 150);
        JLabel screenLabel = layoutBuilder.label(this.DEFAULT);
        layoutBuilder.addLabel(screenLabel);
        layoutBuilder.build();
        layoutBuilder.dispose();
        return screenLabel;
    }

    /**
     *
     */
    public void run() {
        try {
            this.__main.getLogSemaphore().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        __main.writeLogMessage("[Screen]-set thread name");
        this.setName("Screen Thread");
        __main.writeLogMessage("[Screen]-name settled");
        __main.writeLogMessage("[Screen]-Build GUI");
        JLabel screenLabel = this.buildUI();
        __main.writeLogMessage("[Screen]-GUI builded");
        __main.writeLogMessage("[Screen]-Realising log semaphore");
        this.__main.getLogSemaphore().release();
        this.sleep();
        this.printMessages(screenLabel);
    }
}
