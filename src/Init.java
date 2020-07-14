import entities.DataBase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Init {
    public static File ccsFile;// 根配置文件的File对象（位于程序内部的配置文件，指定数据库文件位置，所有数据库的文件位置随意但必须配置到根配置里面）
    public static File rootDir;// 数据库文件的File对象（即根据根配置文件找到的数据库根目录）
    public static File DataProperty;// 数据库总的详细配置的File对象，与程序主体分离（目前不完善，待开发与设计）
    public static HashMap<String,String> PropMap;// 数据库总的详细配置的map

    public static ArrayList<DataBase> dataBases = new ArrayList<>();// DataBase实体对象存入的唯一总列表

    // 程序的初始化类的构造方法，完成根配置、数据库总配置、每个数据库的独立配置的装载
    public Init() throws Exception {
        // 根配置初始化和根数据库初始化（抛出详细报错）
        {
            ccsFile = new File("Configuration\\CCS.property");
            if (ccsFile.exists() && ccsFile.isFile()) {
                BufferedReader brCCS = new BufferedReader(new FileReader(ccsFile));
                String readLine = brCCS.readLine();
                String[] rootPath = readLine.split(" >> ");

                if (rootPath[0].equals("mainProperty")) {
                    rootDir = new File(rootPath[1]);
                    if (rootDir.exists() && rootDir.isDirectory()) {
                        DataProperty = new File(rootDir, ".coreDB/_database.property");
                        BufferedReader brProp = new BufferedReader(new FileReader(DataProperty));
                        PropMap = new HashMap<>();

                        String readProp;
                        while (true) {
                            readProp = brProp.readLine();
                            if (readProp == null) {
                                break;
                            }
                            String[] split = readProp.split(":");
                            PropMap.put(split[0], split[1]);
                        }
                        brProp.close();
                    } else {
                        throw new Exception("找不到根数据库配置文件");
                    }
                } else {
                    brCCS.close();
                    throw new Exception("配置文件错误，请检查根配置文件");
                }
            } else {
                throw new Exception("找不到根配置文件");
            }
        }

        // 各个用户数据库初始化（新建DataBase实体对象存入唯一总列表）
        {
            File[] listFiles = rootDir.listFiles();
            for (File ret :
                    listFiles) {
                if (ret.isDirectory() && !ret.getName().equals(".coreDB")) {
                    dataBases.add(new DataBase(ret));
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            new Init();
//            HashMap<String,String> propMap = Init.PropMap;
//            for (String key :
//                    propMap.keySet()) {
//                System.out.print(key+":");
//                System.out.println(propMap.get(key));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}