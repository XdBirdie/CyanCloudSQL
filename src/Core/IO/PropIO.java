package Core.IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

public class PropIO {

    public static boolean saveProp(File propFile, HashMap<String,String> propMap) throws Exception {
        
        // 线程锁锁住操作的File对象，防止交叉操作，而不同的File文件读写不受影响
        synchronized (propFile){
            // 缓冲写入器
            BufferedWriter bwProp = new BufferedWriter(new FileWriter(propFile,false));

            // 把PropMap写入对应配置文件
            for (String key :
                    propMap.keySet()) {
                bwProp.write(key+":"+propMap.get(key) +"\n");
            }

            // 关闭缓冲写入器
            bwProp.close();
        }

        return true;
    }
}
