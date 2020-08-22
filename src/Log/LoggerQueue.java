package Log;

import Log.Entity.LogRoot;
import Log.LogQueue.QueueController;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LoggerQueue {


    private volatile static ConcurrentLinkedQueue<LogRoot> logQueue;

    {
        logQueue = new ConcurrentLinkedQueue<>();
    }

    public synchronized void push(String detail, String type){
        LogRoot log = new LogRoot(detail,type);
        logQueue.offer(log);
        this.notifyAll();
    }

    public synchronized void push(LogRoot log){
        logQueue.offer(log);
        this.notifyAll();
    }

    public synchronized LogRoot pop(){
        if (logQueue.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return logQueue.poll();
    }


}
