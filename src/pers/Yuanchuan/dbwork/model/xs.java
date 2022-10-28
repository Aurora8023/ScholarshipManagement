package pers.Yuanchuan.dbwork.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class xs {
    private final SimpleStringProperty stuId;
    private final SimpleStringProperty stuName;
    private final SimpleStringProperty stuMajor;
    private final SimpleIntegerProperty sum;

    public xs(String stuId, String stuName, String stuMajor, Integer sum) {
        super();
        this.stuId = new SimpleStringProperty(stuId);
        this.stuName = new SimpleStringProperty(stuName);
        this.stuMajor = new SimpleStringProperty(stuMajor);
        this.sum = new SimpleIntegerProperty(sum);
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

    public int getSum() {
        return sum.get();
    }

    public SimpleIntegerProperty sumProperty() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum.set(sum);
    }
}
