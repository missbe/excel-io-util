import bean.Blog;
import bean.Person;
import excel.WorkbookXssHss;
import util.FileUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class XssFWorkbookTest {
    private static String filePath    = "/tmp/blog";
    private WorkbookXssHss xssFWorkbook = WorkbookXssHss.getInstance(filePath, WorkbookXssHss.WORKBOOK_XSSF);

    public static  Map<Integer, Person> generateMap(){
        Map<Integer, Person> personInfoMap = new TreeMap<Integer, Person>();

        System.out.println("------------------初始化数据对象------------------");
        for (int i = 0, n=10; i < n; i++) {
            Person person=new Person("51370119950813000"+i,"love1208tt@foxmail.com",23+i);
            personInfoMap.put(Integer.valueOf(i), person);
        }
        System.out.println("------------------初始化数据对象完成------------------");

        return personInfoMap;
    }

     @Test
    public void testCreateXSSWorkbook() throws IOException {
        String filePath = "/excel/test.xlsx";
        File file = FileUtil.createFile(filePath);

        ////检查文件是否存在
        if(file.exists() && file.isFile()){
            System.out.println("文件：" + file.getName() + "成功打开！");
        }else{
            System.out.println("文件：" + file.getName() + "不存在或文件为目录!！");
            return;
        }

        ///create XSSWorkbook
        XSSFWorkbook  xssfWorkbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("missbe");

        xssfSheet.setDefaultColumnWidth(12);
        xssfSheet.setColumnWidth(1,20);
        xssfSheet.setColumnWidth(5,20);
        ///初始化列表头
        XSSFRow xssfRowHead = xssfSheet.createRow(0);
        Cell cellHead = xssfRowHead.createCell(1);
        cellHead.setCellValue("身份编号");

        cellHead = xssfRowHead.createCell(2);
        cellHead.setCellValue("姓名");

        cellHead = xssfRowHead.createCell(3);
        cellHead.setCellValue("年龄");

        cellHead = xssfRowHead.createCell(4);
        cellHead.setCellValue("性别");

        cellHead = xssfRowHead.createCell(5);
        cellHead.setCellValue("地址");

         Map<Integer, Person> personInfoMap = generateMap();
         int rowId = 1;
        for (Integer key : personInfoMap.keySet()){
            XSSFRow xssfRow = xssfSheet.createRow(rowId++);
            Person person = personInfoMap.get(key);

            assert person != null;

            int colId = 1;
            Cell cell = xssfRow.createCell(colId++);
            cell.setCellValue(person.getId());

            cell = xssfRow.createCell(colId++);
            cell.setCellValue(person.getName());

            cell = xssfRow.createCell(colId++);
            cell.setCellValue(person.getAge());

            cell = xssfRow.createCell(colId++);
            cell.setCellValue(person.getSex().toString());

            cell = xssfRow.createCell(colId++);
            cell.setCellValue(person.getAddress());
        }
        ///create xlsx file
        OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
        xssfWorkbook.write(os);
        os.close();
        System.out.println("创建并写入数据完成！！!");
    }

    @Test
    public void readFromExcel() {
         xssFWorkbook = WorkbookXssHss.getInstance(filePath, WorkbookXssHss.WORKBOOK_XSSF);
        List<Blog> datas = xssFWorkbook.readFromExcel(Blog.class);

        for (Blog blog :datas){
            System.out.println(blog);
        }
    }

    @Test
    public void writeToExcel() {
        List<Object> objectInfoList = ReflectClassTest.generateBlogList();
        xssFWorkbook = WorkbookXssHss.getInstance(filePath,WorkbookXssHss.WORKBOOK_XSSF);
        xssFWorkbook.setSheetName("没有神的过往");
        xssFWorkbook.writeToExcel(objectInfoList);
    }
}
