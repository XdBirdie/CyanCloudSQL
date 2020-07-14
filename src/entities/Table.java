package entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

public class Table {
    public HashMap<String,String> PropMap = new HashMap<>();// 配置map
    public HashMap<String,String> columnMap = new HashMap<>();// 列map
    public File tableRoot;// 根目录file

    private File dataFile;// 数据文件

    public Table(File tableFile) throws Exception {
        this.tableRoot = tableFile;
        File tableProp = new File(this.tableRoot,"/_table.property");
        File column = new File(this.tableRoot,"/_column.property");
        dataFile = new File(this.tableRoot,"/_data1.data");
        // 配置载入
        {
            BufferedReader brProp;
            try {
                brProp = new BufferedReader(new FileReader(tableProp));
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

        // 列载入
        {
            BufferedReader brColumn;
            try {
                brColumn = new BufferedReader(new FileReader(column));
            } catch (FileNotFoundException e) {
                throw new Exception("数据表列打开失败");
            }
            String readLine;
            while(true){
                readLine = brColumn.readLine();
                if(readLine == null){
                    break;
                } else{
                    String[] split = readLine.split(" ");
                    columnMap.put(split[0],split[1]);
                }
            }
            brColumn.close();
        }
    }
}
