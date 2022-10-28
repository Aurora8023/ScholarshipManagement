package pers.Yuanchuan.dbwork.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import pers.Yuanchuan.dbwork.dao.ScholarshipDao;
import pers.Yuanchuan.dbwork.model.Scholarship;
import pers.Yuanchuan.dbwork.utils.Db_utils;
import pers.Yuanchuan.dbwork.utils.String_isEmpty;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class AddScholarController {

    @FXML
    private TextField id_text;

    @FXML
    private TextField name_text;

    @FXML
    private TextField year_text;

    @FXML
    private TextField company_text;

    @FXML
    private Button insert_button;

    private String level="null";

    @FXML
    private RadioButton country_level;

    @FXML
    void country_level_action(ActionEvent event) {
        level="国家级";
    }

    @FXML
    private RadioButton province_level;

    @FXML
    void province_level_action(ActionEvent event) {
        level="省级";
    }

    @FXML
    private RadioButton school_level;


    @FXML
    void school_level_action(ActionEvent event) { level="校级"; }

    @FXML
    private Button reset_button;

    private Db_utils dbUtils=new Db_utils();
    private ScholarshipDao scholarshipDao=new ScholarshipDao();

    /*
     *插入新的奖学金信息
     */
    @FXML
    void insert_scholar(ActionEvent event) throws Exception {
        if(String_isEmpty.isEmpty(this.id_text.getText())||String_isEmpty.isEmpty(this.name_text.getText())||String_isEmpty.isEmpty(this.year_text.getText())||String_isEmpty.isEmpty(this.company_text.getText())||level.equals("null")){
            Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
            alert.setTitle("好像出了点问题...");
            alert.setHeaderText("所有信息必须完整!"); // 设置对话框的头部文本
            alert.setContentText("请重新修改后再添加!");
            alert.setResizable(false);
            // 设置对话框的内容文本
            alert.show(); // 显示对话框
        }
        else{
            int id=Integer.parseInt(this.id_text.getText());
            Scholarship scholarship = new Scholarship(id,"","",0,"");
            ResultSet rs = scholarshipDao.query(dbUtils.getCon(),scholarship);
            String name=this.name_text.getText();
            int year=Integer.parseInt(this.year_text.getText());
            String issuer=this.company_text.getText();
            Connection con=null;
            try {
                con= dbUtils.getCon();
                if(rs.isBeforeFirst()){
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("奖学金类别添加失败");
                    alert.setHeaderText("该编号已存在对应的奖学金"); // 设置对话框的头部文本
                    alert.setResizable(false);
                    // 设置对话框的内容文本
                    alert.show(); // 显示对话框
                    this.id_text.setText("");
                    this.name_text.setText("");
                    this.year_text.setText("");
                    this.company_text.setText("");
                }
                else{
                    scholarship.setId(id);
                    scholarship.setScholarName(name);
                    scholarship.setScholarRank(level);
                    scholarship.setScholarYear(year);
                    scholarship.setScholarIssuer(issuer);
                    int n=scholarshipDao.add(con,scholarship);
                    if(n==1){
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("奖学金类别添加成功");
                        alert.setHeaderText(name+"添加成功!"); // 设置对话框的头部文本
                        alert.setResizable(false);
                        // 设置对话框的内容文本
                        alert.show(); // 显示对话框
                    }
                    else {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("奖学金类别添加失败");
                    alert.setHeaderText(name+"添加失败“"); // 设置对话框的头部文本
                    alert.setResizable(false);
                    // 设置对话框的内容文本
                    alert.show(); // 显示对话框
                }
                    this.id_text.setText("");
                    this.name_text.setText("");
                    this.year_text.setText("");
                    this.company_text.setText("");
                }

//
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void reset_text(ActionEvent event) {
        this.id_text.setText("");
        this.name_text.setText("");
        this.year_text.setText("");
        this.company_text.setText("");
    }

    Parent createNode() throws IOException {
        return FXMLLoader.load(getClass().getResource("add_scholarship.fxml"));
    }
}
