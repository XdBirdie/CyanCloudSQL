package Entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

public class Table {
    public HashMap<String,String> PropMap = new HashMap<>();// 配置map
    public HashMap<String,String> columnMap = new HashMap<>();// 列map
    public File tableRoot;// 根目录file

    private File dataFile;// 数据文件夹
    public File tablePropFile;// 表格基础配置文件
    public File columnFile;// 列配置文件

    // 构造器
    public Table(File tableFile) throws Exception {
        this.tableRoot = tableFile;
        tablePropFile = new File(this.tableRoot,"/_table.property");
        columnFile = new File(this.tableRoot,"/_column.property");
        dataFile = new File(this.tableRoot,"/data/");
        // 配置载入（从配置文件载入到PropMap）
        {
            BufferedReader brProp;
            try {
                brProp = new BufferedReader(new FileReader(tablePropFile));
            } catch (FileNotFoundException e) {
                throw new Exception("数据表配置打开失败");
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

        // 列载入（从列配置文件载入到columnMap）
        {
            BufferedReader brColumn;
            try {
                brColumn = new BufferedReader(new FileReader(columnFile));
            } catch (FileNotFoundException e) {
                throw new Exception("数据表列打开失败");
            }
            String readLine;
            while(true){
                readLine = brColumn.readLine();
                if(readLine == null){
                    break;
                } else{
                    String[] split = readLine.split(":");
                    columnMap.put(split[0],split[1]);
                }
            }
            brColumn.close();
        }
    }

    
}
