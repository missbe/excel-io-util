/*
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:excel_io_util
 *   Date:18-8-6 下午3:30
 *   @author lyg
 *   @version 1.0
 */

package excel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.ExcelIOUtil;
import util.FileUtil;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WorkbookXssHss implements IExcelReadWrite {
    public final  static  int WORKBOOK_XSSF        = 2 >> 2;
    public final  static  int WORKBOOK_HSSF        = 2 >> 3;
    private  static WorkbookXssHss WORKBOOK        = null;
    private static String sheetName                = "MISSBE工作表";
    private static  String filePath;
    private static  String FILE_SUFFIX ;
    private Workbook workbook;

    private WorkbookXssHss(int flag){
        if(flag == WORKBOOK_XSSF){
            workbook = new XSSFWorkbook();
        }else if(flag == WORKBOOK_HSSF){
            workbook = new HSSFWorkbook();
        }
    }

    /**
     * 初始化工作表，生成工作表对象，默认XLSX文件
     @param filePath 工作表创建路径,路径不加后缀名.如/excel/tmp
      *                 表示在根路径下的excel文件夹内创建tmp.xlsx文件
     * @param
     */
    public static  WorkbookXssHss getInstance(String filePath,int flag){
        ////根据文件类型更改文件后缀名称
        if(flag == WORKBOOK_HSSF){
            FILE_SUFFIX = ".xls";
        }else if(flag == WORKBOOK_XSSF){
            FILE_SUFFIX = ".xlsx";
        }
        WorkbookXssHss.setFilePath(filePath + FILE_SUFFIX);
        FileUtil.createFile(WorkbookXssHss.filePath);

        if(WORKBOOK ==null){
            return WORKBOOK = new WorkbookXssHss(flag);
        }

        WORKBOOK.setSheetName(sheetName);
        return WORKBOOK;
    }

    public static void setFilePath(String filePath) {
        WorkbookXssHss.filePath = filePath;
    }

    /**
     * 返回指定类型的对象List，该对象应该有默认的构造方法，
     * 否则会报InstantiationException异常，获取不到数据
     * @param clazz 返回列表中对象的类型
     * @exception InstantiationException IllegalAccessException
    **/
    public <T> List<T> readFromExcel(Class<T> clazz) {
        ////加载指定路径下的excel文件
        try (
                InputStream fis = new BufferedInputStream(new FileInputStream(new File(filePath)))  ;
        ){
            ////根据文件名后缀判断文件类型，选择工作表对象
                if(workbook instanceof  HSSFWorkbook){
                    workbook = new HSSFWorkbook((fis));
                }else if(workbook instanceof XSSFWorkbook){
                    workbook = new XSSFWorkbook(fis);
                }
        } catch (FileNotFoundException e) {
            System.out.println("------------------查找文件失败，详细信息------------------" );
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("------------------读取文件失败，详细信息------------------");
            e.printStackTrace();
            System.exit(-1);
        }
        ////断言工作表对象是否为空
        assert workbook != null;

        ////读取excel文件
        List<T> results = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        ///跳过设置的表头数据
        if(rowIterator.hasNext()){
            rowIterator.next();
        }

        while (rowIterator.hasNext()){
            Row row = (Row)rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            Field[] fields = clazz.getDeclaredFields();
            T object = null; ///保存数据的中间对象
            try {
                object = clazz.newInstance();
            } catch (InstantiationException e) {
                System.out.println("------------------初始化泛型对象失败(检查对象的无参构造是否存在)详细信息------------------");
                e.printStackTrace();
                continue;
            } catch (IllegalAccessException e) {
                System.out.println("------------------违法获取异常，详细信息------------------");
                e.printStackTrace();
                continue;
            }
            for (Field field : fields){
                if(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    ///TEST CELL FIELD
//                    System.out.println("------CELL:" + cell + "-------FIELD:" + field.getName());

                    ///设置可访问私有字段，否则报IllegalAccessException异常
                    field.setAccessible(true);
                    switch (cell.getCellTypeEnum()){
                        case STRING:
                            try {
                                field.set(object, cell.getStringCellValue());
                            } catch (IllegalAccessException e) {
                                System.out.println("------------------为对象设置字符串数据失败，详细信息------------------" );
                                e.getLocalizedMessage();
                            }
                            break;
                        case NUMERIC:
                            try {
                                double tmp = cell.getNumericCellValue();
                                String fieldClassName = field.getType().getName();
                                /**
                                 * 解决从文件中读取数字都是double类型的问题
                                 **/
                                if(fieldClassName.endsWith("Long") || fieldClassName.endsWith("long")){
                                    field.set(object, (long)tmp);
                                }else if(fieldClassName.endsWith("Integer") || fieldClassName.endsWith("int")){
                                    field.set(object, (int)tmp);
                                }else if(fieldClassName.endsWith("Float") || fieldClassName.endsWith("float")){
                                    field.set(object, (float)tmp);
                                }

                            } catch (IllegalAccessException e) {
                                System.out.println("------------------为对象设置数字数据失败，详细信息------------------" );
                                e.printStackTrace();
                            }
                            break;
                        case FORMULA:
                            try {
                                field.set(object, cell.getCellFormula());
                            } catch (IllegalAccessException e) {
                                System.out.println("------------------为对象设置公式数据失败，详细信息------------------" );
                                e.printStackTrace();
                            }
                            break;
                    }///end switch
                }///end if
            }///end inner for fields
            ///将数据添加进List集合
            if(object != null )
                results.add(object);
//            System.out.println();
        }///end outter while
        return results;
    }

    /**
     * 该方法将给定的对象列表数据写入到指定的excel文件当中去
     * @param objectInfoList 将要定入excel文件的对象列表
     */
    public void writeToExcel(List<Object> objectInfoList) {
        Map<Integer, List<Object>> datas = ExcelIOUtil.parseData(objectInfoList);
        Sheet sheet = workbook.createSheet();
        if(!sheet.getSheetName().equals(sheetName)){
            workbook.setSheetName(0, sheetName);
        }
        sheet.setDefaultColumnWidth(18);

       ////将MAP中的数据写入工作表内存
        for (Integer it : datas.keySet()){
            Row row = sheet.createRow(it.intValue());
            List<Object> columnList = datas.get(it);
            for (int col = 0; col < columnList.size(); col++){
                Cell cell = row.createCell(col);
               ////将要写入的数据进行强制类型转换写入文件
                String className = columnList.get(col).getClass().getName();
                if(className.endsWith("String")){
                    cell.setCellValue((String)columnList.get(col));
                }else if(className.endsWith("Long") || className.endsWith("long")){
                    cell.setCellValue((long)columnList.get(col));
                }else if(className.endsWith("Integer") || className.endsWith("int")){
                    cell.setCellValue((int)columnList.get(col));
                }else if(className.endsWith("Double") || className.endsWith("double")){
                    cell.setCellValue((double)columnList.get(col));
                }else if(className.endsWith("Float") || className.endsWith("float")){
                    cell.setCellValue((float)columnList.get(col));
                }
            }///end inner for
        }///end outter for

       ////将工作表内存中数据写入到文件中
        try(
                OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
        ) {
                workbook.write(os);
        } catch (FileNotFoundException e) {
            System.out.println("---------查找文件出现异常！详细信息---------");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("---------写入文件出现异常！详细信息---------");
            e.printStackTrace();
        }

        System.out.println("------------------完成数据写入到文件---------------");
    }

    /**
     * 对工作簿的工作表进行命名,必须要在进行写入文件方法writeToExcel前面调用才有效
     * @param sheetName 工作表的名称
    * */
    public  void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
