package Core;

import Core.Entities.DataBase;
import Log.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

// 该类使用单例模式
public class Init {
    private static Init theInit;

    public static File ccsFile;// 根配置文件的File对象（位于程序内部的配置文件，指定数据库文件位置，所有数据库的文件位置随意但必须配置到根配置里面）

    public static File rootDir;// 数据库文件的File对象（即根据根配置文件找到的数据库根目录）
    public static File DataProperty;// 数据库总的详细配置的File对象，与程序主体分离（目前不完善，待开发与设计）
    public static HashMap<String,String> PropMap;// 数据库总的详细配置的map

    public static ArrayList<DataBase> dataBases = new ArrayList<>();// DataBase实体对象存入的唯一总列表

    private Init() throws Exception {
        /**
         * @title Core.Init
         * @description TODO 程序的初始化类的构造方法，完成根配置、数据库总配置、每个数据库的独立配置的装载
         * @param  
         * @return : null 
         * @throws Exception
         * @author Ordi_P
         * @date 2020/8/12 21:11 
         */

        // 根配置初始化和根数据库初始化（抛出详细报错）
        {
            ccsFile = new File("Configuration\\CCS.property");// 创建根配置文件对象

            // 先判断文件是否存在和是否为一个文件
            if (ccsFile.exists() && ccsFile.isFile()) {
                BufferedReader brCCS = new BufferedReader(new FileReader(ccsFile));// 创建一个缓冲输入
                String readLine = brCCS.readLine();// 读取其中的数据
                String[] rootPath = readLine.split(" >> ");// 基于间隔符拆分一行键值对

                // 首先判断是否为 mainProperty 数据
                if (rootPath[0].equals("mainProperty")) {
                    // 是，即获得其值作为数据库文件根目录对象
                    rootDir = new File(rootPath[1]);
                    // 判断数据库根目录是否存在
                    if (rootDir.exists() && rootDir.isDirectory()) {
                        Logger.info("程序根配置读取成功");

                        // 获得数据库总的详细配置的File对象
                        DataProperty = new File(rootDir, ".coreDB/_database.property");
                        // 判断是否存在
                        if (DataProperty.exists() && DataProperty.isFile()) {
                            try (BufferedReader brProp = new BufferedReader(new FileReader(DataProperty))) {
                                PropMap = new HashMap<>();

                                // 读取配置参数
                                String readProp;
                                while (true) {
                                    readProp = brProp.readLine();
                                    if (readProp == null) {
                                        break;
                                    }
                                    String[] split = readProp.split(":");
                                    PropMap.put(split[0], split[1]);
                                }
                                Logger.info("数据库根配置读取完成");
                            } catch (Exception e){
                                throw new Exception("数据库根配置内容异常");
                            }
                        } else {
                            throw new Exception("数据库根配置文件异常");
                        }
                    } else {
                        throw new Exception("数据库根目录异常");
                    }
                } else {
                    brCCS.close();
                    throw new Exception("程序配置文件错误，请检查根配置文件");
                }
            } else {
                throw new Exception("程序根配置文件错误");
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

        Logger.info("程序完成初始化");
    }

    public synchronized static Init getInstance(){
        /**
         * @title getInstance
         * @description TODO Double Check Lock（双重校验加锁机制实现单例模式）
         * @param
         * @return : Core.Init
         * @throws null
         * @author Ordi_P
         * @date 2020/8/12 21:12
         */

        if(theInit == null){
            synchronized (Init.class) {
                if (theInit == null){
                    try {
                        theInit = new Init();
                    } catch (Exception e) {
                        e.printStackTrace();
                        theInit = null;
                    }
                }
            }
        }

        return theInit;
    }
}