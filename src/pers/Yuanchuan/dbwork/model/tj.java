package pers.Yuanchuan.dbwork.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class tj {
    private final SimpleStringProperty stuMajor;
    private final SimpleIntegerProperty sum;
    public tj(String stuMajor, Integer sum) {
        super();
        this.stuMajor = new SimpleStringProperty(stuMajor);
        this.sum = new SimpleIntegerProperty(sum);
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
