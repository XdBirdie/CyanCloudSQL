package Log;

import Log.Entity.LogRoot;

import java.util.ArrayList;

/**
 * @author Ordi_P
 * @title: LogInit
 * @projectName CyanCloudSQL
 * @description: TODO 自主搭建的日志框架的初始化
 * @date 2020/8/1221:48
 */
public class LogInit {
    public static final LogInit logInitInstance = new LogInit();

    public static ArrayList<LogRoot> logs = new ArrayList<>();

    private LogInit() {
    }
}
