import Core.Init;
import Core.Entities.DataBase;
import Core.Entities.Table;

/**
 * @author Ordi_P
 * @title: Main
 * @projectName CyanCloudSQL
 * @description: TODO 程序运行的入口
 * @date 2020/8/1221:08
 */
public class Main {
    public static void main(String[] args) {
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
