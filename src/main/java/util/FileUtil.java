/*
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:excel_io_util
 *   Date:18-8-6 下午3:34
 *   @author lyg
 *   @version 1.0
 */

package util;import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static void ceateDir(String dirPath){
        File file = new File(dirPath);
        if(!file.exists()){
            file.mkdir();
            System.out.println("------------------文件夹："+ dirPath + " 创建成功------------------");
        }else{
            System.out.println("------------------文件夹："+ dirPath + " 已经存在------------------");
        }

    }
    public static File createFile(String filePath){
        File file = new File(filePath);

        ////文件夹不存在时先创建文件夹
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdir();
            System.out.println("------------------文件夹："+  file.getParentFile() + " 创建成功------------------");
        }

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("---------文件：" + filePath + " 创建出现读写异常---------");
                System.out.println("---------异常原因---------" );
                System.exit(-1);
            }
            System.out.println("------------------文件："+ filePath + " 创建成功------------------");
        }else{
            System.out.println("------------------正在读取文件："+ filePath + "------------------");
        }
        return file;
    }

}
