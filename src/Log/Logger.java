package Log;

import Log.LogQueue.QueueController;

/**
 * @author Ordi_P
 * @title: Logger
 * @projectName CyanCloudSQL
 * @description: TODO 日志系统总调度
 * @date 2020/8/1221:48
 */
public class Logger {
    private static QueueController queueController = QueueController.getInstance();

    public static void info(String detail) {
        queueController.addLogToQueue(detail,"INFO");
    }

    public static void error(String detail) {
        queueController.addLogToQueue(detail,"ERROR");
    }
}