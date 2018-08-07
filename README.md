### excel-io-util EXCEL工具，方便快捷的将数据读取和写入指定路径下的EXCEL文件。
**实现功能**
+ 利用POI操作excel文件，实现对指定路径下的excel文件操作；
+ 支持97-2003版本的Excel文件（后缀名为xls）；
+ 支持2007及以后版本的Excel文件（后缀名为xlsx）；

**具体操作**
+ 文件路径（filePath）只用指定文件名；例如：filePath    = "/tmp/blog"表示在当前文件根路径下的tmp的文件夹下的名字为blog的excel文件；
+ Excel文件后缀WorkbookXssHss.WORKBOOK_XSSF和WorkbookXssHss.WORKBOOK_HSSF确定，WORKBOOK_XSSF表示后缀为XLSX文件（2007以后），WORKBOOK_HSSF表示后缀名为XLS文件（97-2003版本）。
```
///获取指定路径下的文件，如果不存在文件会自动进行创建
private static String filePath    = "/tmp/blog"; 
private WorkbookXssHss xssFWorkbook = WorkbookXssHss.getInstance(filePath, WorkbookXssHss.WORKBOOK_XSSF);
////获取文件中的信息，返回指定类型的对象列表
List<Blog> datas = xssFWorkbook.readFromExcel(Blog.class);
/////将列表中的对象，以类中字段顺序写入excel文件
List<Object> objectInfoList ;
 xssFWorkbook = WorkbookXssHss.getInstance(filePath,WorkbookXssHss.WORKBOOK_XSSF);
        xssFWorkbook.setSheetName("没有神的过往");
        xssFWorkbook.writeToExcel(objectInfoList);
```

### excel-io-util-v1.0

***V1.0 Bug 日志***
+ 写入文件中的Bean对象必须存在无参构造方法，否则会出现初始化对象失败；
+ 读取文件时需要指定正确的类，类型不匹配将会出现错误；

**Maven依赖**
```
<!-- https://mvnrepository.com/artifact/org.apache.poi/poi -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>3.17</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>3.17</version>
        </dependency>
```
**Java版本**
+ JDK 1.8

***[GitHub地址](https://github.com/missbe/excel-io-util,"excel-io-util")***
