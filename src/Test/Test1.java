package Test;

import Log.Logger;

/**
 * @author Ordi_P
 * @title: Test1
 * @projectName CyanCloudSQL
 * @description: TODO Test
 * @date 2020/8/1317:34
 */
public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        Logger.info("日志系统开始运行");
        Thread.sleep(10);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 50000; i++) {
            new Thread(new MyThread("m"+i)).start();
            Logger.info("l"+i);
        }

        long end = System.currentTimeMillis();

        System.out.println("Time:"+(end-start));
    }
}

class MyThread implements Runnable{
    String detail;

    public MyThread(String detail) {
        this.detail = detail;
    }

    @Override
    public void run() {
        Logger.info(detail);
    }
}
