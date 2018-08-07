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
import java.util.List;

public interface IExcelReadWrite {
     <T> List<T> readFromExcel(Class<T> clazz);
     void writeToExcel(List<Object> objectInfoList);
}
