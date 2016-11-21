import javax.swing.*;
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
    private PrintWriter __writer;


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
        try {
            this.__writer = new PrintWriter(logPath, "UTF-8");
            this.__logQueue = new LinkedBlockingQueue<String>();

        } catch (Exception e) {
            System.out.println("Error writing file");
        }
    }

    public void run() {

        this.setName("Log Thread");
        this.sleep();
        this.saveLogs();
        this.__writer.close();
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
            this.__writer.println(message);
            System.out.println(message);
        }
        this.sleep();
        this.saveLogs();
    }

    public String buildMessage(String message) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date) + ": " + message;
    }


}
