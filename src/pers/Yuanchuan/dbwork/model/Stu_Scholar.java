package pers.Yuanchuan.dbwork.model;

import javafx.beans.property.SimpleStringProperty;

public class Stu_Scholar {
    private final SimpleStringProperty stuId;
    private final SimpleStringProperty stuName;
    private final SimpleStringProperty stuMajor;
    private final SimpleStringProperty scholarName;

    private final SimpleStringProperty exam;

    private final SimpleStringProperty state;//奖金状态

    public Stu_Scholar(String stuId,String stuName,
                       String stuMajor,String scholarName,String exam,String state){
        super();
        this.stuId=new SimpleStringProperty(stuId);
        this.stuName=new SimpleStringProperty(stuName);
        this.stuMajor=new SimpleStringProperty(stuMajor);
        this.scholarName=new SimpleStringProperty(scholarName);
        this.exam=new SimpleStringProperty(exam);
        this.state=new SimpleStringProperty(state);
    }

    public String getStuId() {
        return stuId.get();
    }

    public SimpleStringProperty stuIdProperty() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId.set(stuId);
    }

    public String getStuName() {
        return stuName.get();
    }

    public SimpleStringProperty stuNameProperty() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName.set(stuName);
    }

    public String getStuMajor() {
        return stuMajor.get();
    }

    public SimpleStringProperty stuMajorProperty() {
        return stuMajor;
    }

    public void setStuMajor(String stuMajor) {
        this.stuMajor.set(stuMajor);
    }

    public String getScholarName() {
        return scholarName.get();
    }

    public SimpleStringProperty scholarNameProperty() {
        return scholarName;
    }

    public void setScholarName(String scholarName) {
        this.scholarName.set(scholarName);
    }

    public String getExam() {
        return exam.get();
    }

    public SimpleStringProperty examProperty() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam.set(exam);
    }

    public String getState() {
        return state.get();
    }

    public SimpleStringProperty stateProperty() {
        return state;
    }

    public void setState(String state) {
        this.state.set(state);
    }
}
