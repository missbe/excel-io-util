
/*
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:excel_io_util
 *   Date:18-8-6 上午10:27
 *   @author lyg
 *   @version 1.0
 */

package bean;

public class Person {
    @ColumnName(name = "身份ID")
    private  String id;
    @ColumnName(name = "人员姓名")
    private  String name;
    @ColumnName(name = "人员年龄")
    private  int age;
    @ColumnName(name = "人员性别")
    private  String sex = "男";
    @ColumnName(name = "人员地址")
    private  String address;

    public Person() {
    }

    public Person(String id, String name, int age) {
        this.id      = id;
        this.name    = name;
        this.age     = age;
        this.sex     = "男";
        this.address = "Don't Initial.";
    }

    public Person(String id, String name, int age, String sex , String address) {
        this.id      = id;
        this.name    = name;
        this.age     = age;
        this.sex     = sex;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", address='" + address + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
