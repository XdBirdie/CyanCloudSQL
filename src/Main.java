import Core.Init;
import Core.Entities.DataBase;
import Core.Entities.Table;
import Log.Logger;

import java.io.*;

/**
 * @author Ordi_P
 * @title: Main
 * @projectName CyanCloudSQL
 * @description: TODO 程序运行的入口
 * @date 2020/8/1221:08
 */
public class Main {
    public static void main(String[] args) {
//        OriginTextImage 初始字符logo的显示
        try {
            File originTextImageFile = new File("src\\Static\\OriginTextImage.txt");
            BufferedReader br = new BufferedReader(new FileReader(originTextImageFile));
            while (br.ready()){
                System.out.println(br.readLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Logger.info(">>>日志系统开始工作<<<");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Init.getInstance();
            for (DataBase database :
                    Init.dataBases) {
                System.out.println(database.databaseFile.getName() + ":");
                for (Table table :
                        database.tables) {
                    System.out.println("    " + table.tableRoot.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
