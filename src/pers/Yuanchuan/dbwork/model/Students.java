package pers.Yuanchuan.dbwork.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Students {
    private final SimpleStringProperty id;//学号
    private final SimpleStringProperty name;//姓名
    private final SimpleStringProperty sex;//性别
    
    private final SimpleIntegerProperty year;//入学年份

    private final SimpleStringProperty type;//类型
    private final SimpleStringProperty idcard;//身份证号
    private final SimpleStringProperty khyh;//开户银行
    private final SimpleStringProperty yhkh;//银行卡号
    private final SimpleStringProperty banji;//班级
    private final SimpleStringProperty major;//专业
    private final SimpleStringProperty faculty;//学院
    private final SimpleStringProperty fdy;//辅导员


    public Students(String id, String name, String sex, int year, String type, String idcard, String khyh,
                    String yhkh, String banji, String major, String faculty, String fdy) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.sex= new SimpleStringProperty(sex);
        this.year =new SimpleIntegerProperty(year);
        this.type = new SimpleStringProperty(type);
        this.idcard = new SimpleStringProperty(idcard);
        this.khyh = new SimpleStringProperty(khyh);
        this.yhkh = new SimpleStringProperty(yhkh);
        this.banji = new SimpleStringProperty(banji);
        this.major = new SimpleStringProperty(major);
        this.faculty = new SimpleStringProperty(faculty);
        this.fdy = new SimpleStringProperty(fdy);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSex() {
        return sex.get();
    }

    public SimpleStringProperty sexProperty() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    public int getYear() {
        return year.get();
    }

    public SimpleIntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getIdcard() {
        return idcard.get();
    }

    public SimpleStringProperty idcardProperty() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard.set(idcard);
    }

    public String getKhyh() {
        return khyh.get();
    }

    public SimpleStringProperty khyhProperty() {
        return khyh;
    }

    public void setKhyh(String khyh) {
        this.khyh.set(khyh);
    }

    public String getYhkh() {
        return yhkh.get();
    }

    public SimpleStringProperty yhkhProperty() {
        return yhkh;
    }

    public void setYhkh(String yhkh) {
        this.yhkh.set(yhkh);
    }

    public String getBanji() {
        return banji.get();
    }

    public SimpleStringProperty banjiProperty() {
        return banji;
    }

    public void setBanji(String banji) {
        this.banji.set(banji);
    }

    public String getMajor() {
        return major.get();
    }

    public SimpleStringProperty majorProperty() {
        return major;
    }

    public void setMajor(String major) {
        this.major.set(major);
    }

    public String getFaculty() {
        return faculty.get();
    }

    public SimpleStringProperty facultyProperty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty.set(faculty);
    }

    public String getFdy() {
        return fdy.get();
    }

    public SimpleStringProperty fdyProperty() {
        return fdy;
    }

    public void setFdy(String fdy) {
        this.fdy.set(fdy);
    }


}
