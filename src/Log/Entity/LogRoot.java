package Log.Entity;

import java.io.*;
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
    private static File log;
    static {
        log = new File("log/test.log");
        if (!log.exists()){
            try {
                log.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String type;
    private Calendar calendar;
    private String detail;
    private ArrayList<StackTraceElement> stackTraceElements = new ArrayList<>();

    public LogRoot(String detail, String type) {
        this.detail = detail;
        this.calendar = Calendar.getInstance();
        this.type = type;
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

    public String returnInfo(){
        return  String.format(" %04d-%02d-%02d %02d:%02d:%02d " + detail,
                calendar.get(Calendar.YEAR),
                (calendar.get(Calendar.MONTH)+1),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND))
                ;
    }

    public String returnError(){
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            sb.append("    "+stackTraceElement + "\n");
        }
        return returnInfo() + "\n" + sb.toString();
    }

    public void soutLog(){
        switch (type){
            case "INFO":
                System.out.println("[" + type + " ]" + returnInfo());
                break;
            case "ERROR":
                System.out.println("[" + type + "]" + returnError());
                break;
        }
    }

    public void logOutToFile(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(log,true))){
            switch (type){
                case "INFO":
                    bw.write("[" + type + " ]" + returnInfo()+"\n");
                    break;
                case "ERROR":
                    bw.write("[" + type + "]" + returnError()+"\n");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
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
