package Log.LogQueue;

import Log.Entity.LogRoot;
import Log.LoggerQueue;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Ordi_P
 * @title: QueueController
 * @projectName CyanCloudSQL
 * @description: TODO
 * @date 2020/8/1416:26
 */
public class QueueController {
    private volatile static QueueController queueController;

    private static final LoggerQueue logQueue = new LoggerQueue();

    private QueueController() {
        new Thread(new MessageDealThread()).start();
        System.out.println("初始化");
    }

    public void addLogToQueue(String detail, String type){
        LogRoot log = new LogRoot(detail,type);
        logQueue.push(log);
    }

    public static QueueController getInstance(){
        /*
         * @title getInstance
         * @description TODO 单列模式获取唯一对象的方法
         * @param
         * @return : Log.LogQueue.QueueController
         * @throws null
         * @author Ordi_P
         * @date 2020/8/14 18:16
         */
        if (queueController == null){
            synchronized (QueueController.class){
                if (queueController == null){
                    queueController = new QueueController();
                }
            }
        }

        return queueController;
    }

    static class MessageDealThread implements Runnable {
        @Override
        public void run() {
            System.out.println("线程运行中");
            while(true){
                LogRoot log = logQueue.pop();
                log.soutLog();
            }

        }
    }
}

