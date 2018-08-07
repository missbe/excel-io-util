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

package util;
import bean.ColumnName;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelIOUtil {

    public static Map<Integer, List<Object>> parseData(List<Object> objectInfoList){
        List<Object> headList = new ArrayList<Object>();
        Map<Integer, List<Object>> datas = new HashMap<Integer, List<Object>>();

        ////获取字段的别名，作为列表头
        Class clazz = objectInfoList.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name;
            if (field.isAnnotationPresent(ColumnName.class)) {
                name = field.getAnnotation(ColumnName.class).name();
            } else {
                name = field.getName();
            }
            headList.add(name);
//            System.out.print(" 字段别名:" + name);
        }
//        System.out.println();
        datas.put(Integer.valueOf(0), headList);

        ////将数据放入Map中进行写入文件中
        int rowId = 1;
        for (Object o : objectInfoList) {
            Field[] words = o.getClass().getDeclaredFields();
            List<Object> columnList = new ArrayList<Object>();
            for (Field word : words) {
                word.setAccessible(true);
                try {
                    Object tmp = word.get(o);
                    columnList.add(tmp);
                } catch (IllegalAccessException e) {
                    System.out.println("获取对象不合法，详细信息：" + e.getMessage());
                }
            }///end inner for
            datas.put(Integer.valueOf(rowId++), columnList);
        }
        return datas;
    }
}
