/*
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:excel_io_util
 *   Date:18-8-6 下午3:21
 *   @author lyg
 *   @version 1.0
 */

package bean;

import util.DateUtil;
import java.util.Date;

public class Blog {
    @ColumnName(name = "博客ID")
    private long id;
    // 标题
    @ColumnName(name = "博客标题")
    private String title;
    // 内容
    @ColumnName(name = "博客内容")
    private String content;
    // 发布时间
    @ColumnName(name = "博客发布时间")
    private String date;
    // 点击数
    @ColumnName(name = "博客点击数")
    private long click;
    // 作者姓名
    @ColumnName(name = "博客作者姓名")
    private String author;
    // 所属栏目
    @ColumnName(name = "博客所属栏目")
    private String section;

    public Blog(){}

    public Blog(long id, String title, String content) {
        this(id,title,content, DateUtil.formateDateyyyy_MM_dd(new Date()),12,"ADMIN","技术生活");
    }

    public Blog(long id, String title, String content, String date, long click, String author, String section) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.click = click;
        this.author = author;
        this.section = section;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", click=" + click +
                ", author='" + author + '\'' +
                ", section='" + section + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getClick() {
        return click;
    }

    public void setClick(long click) {
        this.click = click;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
