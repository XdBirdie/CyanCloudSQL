package Log.User;

import Log.Entity.LogRoot;
import Log.LogInit;

import java.util.Calendar;

/**
 * @author Ordi_P
 * @title: AddLog
 * @projectName CyanCloudSQL
 * @description: TODO 新建日志的调用接口
 * @date 2020/8/1221:52
 */
public class AddLog {
    public static void Log(String detail){
        LogRoot log = new LogRoot(detail);
        LogInit.logs.add(log);
        log.soutLog();
    }
}
