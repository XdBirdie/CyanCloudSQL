package Entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBase {
    public HashMap<String,String> PropMap = new HashMap<>();// 配置map

    public File databaseFile;// 根目录file文件
    public File dbPropFile;// 数据库配置文件File

    public ArrayList<Table> tables = new ArrayList<>();

    // 构造器,传入的是该数据库的根目录位置（必须是文件夹）
    public DataBase(File databaseFile) throws Exception {
        // 数据库自身初始化
        {
            this.databaseFile = databaseFile;
            dbPropFile = new File(this.databaseFile,"/_database.property");
            BufferedReader brProp = null;
            try {
                brProp = new BufferedReader(new FileReader(dbPropFile));
            } catch (FileNotFoundException e) {
                throw new Exception("数据库配置打开失败");
            }
            String readLine;
            while(true){
                readLine = brProp.readLine();
                if(readLine == null){
                    break;
                } else{
                    String[] split = readLine.split(":");
                    PropMap.put(split[0],split[1]);
                }
            }
            brProp.close();
        }
        // 每个下属表格的初始化
        {
            File[] listFiles = databaseFile.listFiles();
            for (File ret :
                    listFiles) {
                if (ret.isDirectory()) {
                    tables.add(new Table(ret));
                }
            }
        }
    }
}
