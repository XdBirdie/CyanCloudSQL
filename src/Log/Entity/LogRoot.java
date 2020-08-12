package Log.Entity;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Ordi_P
 * @title: LogRoot
 * @projectName CyanCloudSQL
 * @description: TODO 日志实体父类
 * @date 2020/8/1222:02
 */
public class LogRoot {
    private Calendar calendar;
    private String detail;
    private ArrayList<StackTraceElement> stackTraceElements = new ArrayList<>();

    public LogRoot(String detail) {
        this.detail = detail;
        this.calendar = Calendar.getInstance();
        for (StackTraceElement element :
                Thread.currentThread().getStackTrace()) {
            if (element.getClassName().equals("java.lang.Thread")){
                continue;
            }
            if (element.getClassName().split("\\.")[0].equals("Log")){
                continue;
            }
            stackTraceElements.add(element);
        }
    }

    public void soutLog(){
        System.out.printf("[Log] %04d.%02d.%02d %02d:%02d:%02d ",
                calendar.get(Calendar.YEAR),
                (calendar.get(Calendar.MONTH)+1),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND)
        );
        System.out.println(detail);
    }

    public void soutStackTrace(){
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            System.out.println("    "+stackTraceElement);
        }
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public String getDetail() {
        return detail;
    }

    public ArrayList<StackTraceElement> getStackTraceElements() {
        return stackTraceElements;
    }
}
