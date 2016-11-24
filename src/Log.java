import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ivoribeiro on 19-11-2016.
 */
public class Log extends Thread {

    private Queue<String> __logQueue;
    private FileIO __writer;


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

    public Log(String logPath) {

        this.__logQueue = new LinkedBlockingQueue<String>();
        this.__writer = new FileIO(logPath);

    }

    public void run() {

        this.setName("Log Thread");
        this.sleep();
        this.saveLogs();
    }

    public void processLog(String message) {
        this.__logQueue.offer(message);
        this.wakeUp();
    }


    private synchronized void saveLogs() {
        while (!this.__logQueue.isEmpty()) {
            String message = this.__logQueue.poll();
            message = this.buildMessage(message);
            //Write line on file
            this.__writer.writeOnFile(message);
            System.out.println(message);
        }
        this.sleep();
        this.saveLogs();
    }

    public String buildMessage(String message) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date) + ": " + message+" \n";
    }


}
