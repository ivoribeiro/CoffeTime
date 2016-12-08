import javax.swing.*;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

class Screen extends Thread {

    private final Queue<ScreenMessage> __messageQueue;
    private final Main __main;
    private final String DEFAULT;
    private final Semaphore __screenSemaphore;


    /**
     * Wake up the current thread
     */
    public synchronized void wakeUp() {
        __main.writeLogMessage("[Screen]-awake order received");
        this.notify();
        __main.writeLogMessage("[Screen]-thread awaked");

    }

    /**
     * Sleep the current thread
     */
    public synchronized void sleep() {
        __main.writeLogMessage("[Screen]-sleeping order received");
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
        this.__screenSemaphore = new Semaphore(1);

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
        __main.writeLogMessage("[Screen]-printMessages method");
        try {
            __main.writeLogMessage("[Screen]-acquiring screen semaphore");
            this.__screenSemaphore.acquire(1);
            __main.writeLogMessage("[Screen]-screen semaphore acquired");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!__messageQueue.isEmpty()) {
            __main.writeLogMessage("[Screen]-getting message on the queue");
            ScreenMessage sm = this.__messageQueue.poll();
            __main.writeLogMessage("[Screen]-setting the message to the screen");
            screenLabel.setText(sm.getMessage());
            try {
                Thread.sleep(sm.getTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        __main.writeLogMessage("[Screen]-setting the message to the default");
        screenLabel.setText(this.DEFAULT);
        __main.writeLogMessage("[Screen]-releasing screen semaphore");
        this.__screenSemaphore.release();
        __main.writeLogMessage("[Screen]- order to sleep thread");
        this.sleep();
        __main.writeLogMessage("[Screen]-writing messages on the queue");
        this.printMessages(screenLabel);
    }

    /**
     * Build the frame UI
     *
     * @return
     */
    private JLabel buildUI() {
        __main.writeLogMessage("[Screen]-buildUI method");
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
        __main.writeLogMessage("[Screen]-Realeasing log semaphore");
        this.__main.getLogSemaphore().release();
        __main.writeLogMessage("[Screen]-Log semaphore released");
        __main.writeLogMessage("[Screen]-Sleeping thread");
        this.sleep();
        __main.writeLogMessage("[Screen]-Thread awaked");
        __main.writeLogMessage("[Screen]-Print messages on queue");
        this.printMessages(screenLabel);
        __main.writeLogMessage("[Screen]-queue messages printed");
    }
}
