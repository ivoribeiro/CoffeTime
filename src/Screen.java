import javax.swing.*;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ivoribeiro on 17-11-2016.
 */
public class Screen extends Thread {

    Queue<String> __messageQueue;
    Main __main;

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


    public Screen(Main main) {
        this.__main = main;
        this.__messageQueue = new LinkedBlockingQueue<String>();
    }


    public void processMessage(String message) {
        __messageQueue.offer(message);
        this.wakeUp();
    }


    public void printMessages(JLabel screenLabel) {
        while (!__messageQueue.isEmpty()) {
            screenLabel.setText(this.__messageQueue.poll());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
        JLabel screenLabel = layoutBuilder.label("Bebidas Quentes aos melhores preços");
        layoutBuilder.addLabel(screenLabel);
        layoutBuilder.build();
        layoutBuilder.dispose();
        return screenLabel;
    }

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
        __main.writeLogMessage("[Screen]-GUI Builded");
        this.__main.getLogSemaphore().release();
        this.sleep();
        this.printMessages(screenLabel);

    }
}
