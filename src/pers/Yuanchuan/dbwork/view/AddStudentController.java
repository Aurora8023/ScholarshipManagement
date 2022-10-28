package pers.Yuanchuan.dbwork.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import pers.Yuanchuan.dbwork.dao.StudentDao;
import pers.Yuanchuan.dbwork.model.Student;
import pers.Yuanchuan.dbwork.model.Students;
import pers.Yuanchuan.dbwork.utils.Db_utils;
import pers.Yuanchuan.dbwork.utils.String_isEmpty;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddStudentController {

    @FXML
    private TextField stu_id_text;//学号
    @FXML
    private TextField stu_name_text;//姓名
    @FXML
    private TextField stu_year_text;//年级
    @FXML
    private TextField stu_idcard_text;//身份证号
    @FXML
    private TextField stu_khyh_text;//开户银行
    @FXML
    private TextField stu_yhkh_text;//银行卡号
    @FXML
    private TextField stu_class_text;//班级
    @FXML
    private TextField stu_major_text;//专业号


    @FXML
    private RadioButton man;

    @FXML
    private RadioButton woman;

    @FXML
    private RadioButton bk;

    @FXML
    private RadioButton zk;

    @FXML
    private RadioButton yjs;



    private Db_utils dbUtils=new Db_utils();
    private StudentDao studentDao=new StudentDao();
    private String type="本科";
    private String sex="男";

    @FXML
    void man_action(ActionEvent event) {
        sex="男";
    }

    @FXML
    void reset_action(ActionEvent event) {
        this.stu_id_text.setText("");
        this.stu_name_text.setText("");
        this.stu_year_text.setText("");
        this.stu_idcard_text.setText("");
        this.stu_khyh_text.setText("");
        this.stu_yhkh_text.setText("");
        this.stu_class_text.setText("");
        this.stu_major_text.setText("");
    }

    @FXML
    void woman_action(ActionEvent event) {
        sex="女";
    }

    @FXML
    void bk_action(ActionEvent event){
        type="本科";
    }
    @FXML
    void zk_action(ActionEvent event){
        type="专科";
    }
    @FXML
    void yjs_action(ActionEvent event){
        type="研究生";
    }

    @FXML
    void yes_action(ActionEvent event) {
        if(String_isEmpty.isEmpty(this.stu_id_text.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
            alert.setTitle("好像出了点问题...");
            alert.setHeaderText("学号不能为空!"); // 设置对话框的头部文本
            alert.setContentText("请重新修改要添加的学生信息");
            alert.setResizable(false);
            // 设置对话框的内容文本
            alert.show(); // 显示对话框
        }
        else if(String_isEmpty.isEmpty(this.stu_name_text.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
            alert.setTitle("好像出了点问题...");
            alert.setHeaderText("学生姓名不能为空!"); // 设置对话框的头部文本
            alert.setContentText("请重新修改要添加的学生信息");
            alert.setResizable(false);
            // 设置对话框的内容文本
            alert.show(); // 显示对话框
        }
        else{
            if(this.stu_year_text.getText().equals("")||this.stu_idcard_text.getText().equals("")
                    ||this.stu_khyh_text.getText().equals("")||this.stu_yhkh_text.getText().equals("")||
                    this.stu_class_text.getText().equals("")||this.stu_major_text.getText().equals("")||(!this.man.isSelected()&&!this.woman.isSelected())||(!this.zk.isSelected()&&!this.bk.isSelected()&&!this.yjs.isSelected()))
            {
                Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                alert.setTitle("好像出了点问题...");
                alert.setHeaderText("各项信息均不能为空!"); // 设置对话框的头部文本
                alert.setContentText("请重新修改要添加的学生信息");
                alert.setResizable(false);
                // 设置对话框的内容文本
                alert.show(); // 显示对话框
            }else{
                String id=this.stu_id_text.getText();
                String name=this.stu_name_text.getText();
                int year= Integer.parseInt(this.stu_year_text.getText());
                String idcard=this.stu_idcard_text.getText();
                String khyh=this.stu_khyh_text.getText();
                String yhkh=this.stu_yhkh_text.getText();
                String banji=this.stu_class_text.getText();
                String major=this.stu_major_text.getText();
                Student student=new Student(id,name,sex,year,type,idcard,khyh,yhkh,banji,major);
                Connection con=null;
                try {
                    con= dbUtils.getCon();
                    ResultSet rs=studentDao.query_basic(con,id);
                    if(rs.isBeforeFirst()){
                        Alert alert=new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("学生信息添加失败");
                        alert.setHeaderText("该学号已经存在对应的学生!"); // 设置对话框的头部文本
                        alert.setContentText("请检查后重新添加");
                        alert.setResizable(false);
                        // 设置对话框的内容文本
                        alert.show(); // 显示对话框
                    }else{
                        rs=studentDao.major(con,student);
                        if(!rs.isBeforeFirst()){
                            Alert alert=new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("学生信息添加失败");
                            alert.setHeaderText("专业号输入有误!"); // 设置对话框的头部文本
                            alert.setContentText("不存在该专业号对应的专业!"); // 设置对话框的头部文本
                            alert.setResizable(false);
                            // 设置对话框的内容文本
                            alert.show(); // 显示对话框
                        }else{
                            int m=studentDao.adduser(con,id);
                            int n=studentDao.add(con,student);
                            if(n==1&&m==1){
                                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("学生信息添加成功");
                                alert.setHeaderText(name+" 同学信息添加成功!"); // 设置对话框的头部文本
                                alert.setResizable(false);
                                // 设置对话框的内容文本
                                alert.show(); // 显示对话框
                            }
                            else {
                                Alert alert=new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("学生信息添加失败");
                                alert.setHeaderText(name+" 同学信息添加失败“"); // 设置对话框的头部文本
                                alert.setResizable(false);
                                // 设置对话框的内容文本
                                alert.show(); // 显示对话框
                            }
                            this.stu_id_text.setText("");
                            this.stu_name_text.setText("");
                            this.stu_year_text.setText("");
                            this.stu_idcard_text.setText("");
                            this.stu_khyh_text.setText("");
                            this.stu_yhkh_text.setText("");
                            this.stu_class_text.setText("");
                            this.stu_major_text.setText("");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
    }

    Parent createNode() throws IOException {
        return FXMLLoader.load(getClass().getResource("add_student1.fxml"));
    }
}
