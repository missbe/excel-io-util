/*
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:excel_io_util
 *   Date:18-8-6 下午1:23
 *   @author lyg
 *   @version 1.0
 */

import bean.Blog;
import bean.Person;
import util.ExcelIOUtil;
import org.junit.Test;
import util.ExcelIOUtil;

import java.lang.reflect.Field;
import java.util.*;

public class ReflectClassTest {

    public static List<Object> generatePersonList(){
        List<Object> objectInfoList = new ArrayList<Object>();
        System.out.println("------------------初始化数据对象------------------");
        for (int i = 0, n=10; i < n; i++) {
            Person person=new Person("51370119950813000"+i,"love1208tt@foxmail.com",23+i);
            objectInfoList.add(person);//
        }
        System.out.println("------------------初始化数据对象完成------------------");
        return objectInfoList;
    }
    public static List<Object> generateBlogList(){
        List<Object> objectInfoList = new ArrayList<Object>();
        System.out.println("------------------初始化数据对象------------------");
        for (int i = 0, n=10; i < n; i++) {
            Blog blog = new Blog(Integer.valueOf(i), "Tiltle"+String.valueOf(i), "模拟博客内容.");
            objectInfoList.add(blog);
        }
        System.out.println("------------------初始化数据对象完成------------------");
        return objectInfoList;
    }

    @Test
    public void testObjectClass(){
        List<Object> objectInfoList = generateBlogList();
        for (Object o : objectInfoList){
            Class clazz = o.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields){
                System.out.printf( " %-6s ", field.getType().getName());
            }
            System.out.println();
        }
    }

    @Test
    public void testReflec() {
        Map<Integer, List<Object>> datas = ExcelIOUtil.parseData(generateBlogList());

        ////输出测试Map获取到的数据
        System.out.println();
        for (Integer it : datas.keySet()){
            List<Object> list = datas.get(it);
            for (Object o : list){
                System.out.printf(" %-6s", o );
            }//end inner for
            System.out.println();
        }

    }
}
