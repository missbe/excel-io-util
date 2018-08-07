/*
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:excel_io_util
 *   Date:18-8-6 下午6:14
 *   @author lyg
 *   @version 1.0
 */

import bean.Blog;
import bean.Person;
import excel.WorkbookXssHss;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class IExcelReadWriteTest {
    private static String filePath    = "/tmp/person";
    private WorkbookXssHss xssFWorkbook = WorkbookXssHss.getInstance(filePath, WorkbookXssHss.WORKBOOK_XSSF);

    @Test
    public void readFromExcel() {
        List<Person> objects = xssFWorkbook.readFromExcel(Person.class);

        for (Person p : objects){
            System.out.println(p);
        }
    }

    @Test
    public void writeToExcel() {
        List<Object> objectList = ReflectClassTest.generatePersonList();
        xssFWorkbook.setSheetName("依然慢节奏");
        xssFWorkbook.writeToExcel(objectList);
    }
}