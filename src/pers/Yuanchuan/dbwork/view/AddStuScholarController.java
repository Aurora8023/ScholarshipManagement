package pers.Yuanchuan.dbwork.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import pers.Yuanchuan.dbwork.dao.ScholarshipDao;
import pers.Yuanchuan.dbwork.dao.StuScholarDao;
import pers.Yuanchuan.dbwork.dao.StudentDao;
import pers.Yuanchuan.dbwork.model.Scholarship;
import pers.Yuanchuan.dbwork.model.Student;
import pers.Yuanchuan.dbwork.utils.Db_utils;
import pers.Yuanchuan.dbwork.utils.String_isEmpty;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class AddStuScholarController {

    @FXML
    private TextField stu_text;

    @FXML
    private TextField scholar_text;

    private Db_utils dbUtils=new Db_utils();
    private StuScholarDao stuScholarDao=new StuScholarDao();


    private String state=null;

    private StudentDao studentDao=new StudentDao();

    private ScholarshipDao scholarshipDao = new ScholarshipDao();


    @FXML
    void no_action(ActionEvent event) {
        stu_text.setText("");
        scholar_text.setText("");

    }

    @FXML
    void is_action(ActionEvent event) {
        state="已发放";
    }
    @FXML
    void not_action(ActionEvent event) {
        state="未发放";
    }

    @FXML
    void yes_action(ActionEvent event) {
        if(String_isEmpty.isEmpty(this.stu_text.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
            alert.setTitle("好像出了点问题...");
            alert.setHeaderText("获奖学生学号不能为空!"); // 设置对话框的头部文本
            alert.setContentText("请输入获奖学生学号");
            alert.setResizable(false);
            // 设置对话框的内容文本
            alert.show(); // 显示对话框
        }
        else if(String_isEmpty.isEmpty(this.scholar_text.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
            alert.setTitle("好像出了点问题...");
            alert.setHeaderText("奖学金编号不能为空!"); // 设置对话框的头部文本
            alert.setContentText("请输入奖学金编号");
            alert.setResizable(false);
            // 设置对话框的内容文本
            alert.show(); // 显示对话框
        }
        else{


            //查询有没有学号对应的学生，以及奖学金编号对应的奖学金



            String stu_id=this.stu_text.getText();
            Integer scholar_id=Integer.parseInt(this.scholar_text.getText());
            Connection con=null;
            try {
                con=dbUtils.getCon();
                ResultSet rs=studentDao.query_basic(con,stu_id);
                if(!rs.isBeforeFirst()){
                    Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                    alert.setTitle("该学生信息不存在!");
                    alert.setHeaderText("不存该学号对应的学生"); // 设置对话框的头部文本
                    alert.setContentText("请确定学生学号是否正确");
                    alert.setResizable(false);
                    alert.show();
                }else{
                    Scholarship scholarship = new Scholarship(scholar_id,"","",0,"");
                    rs= scholarshipDao.query(con,scholarship);
                    if(!rs.isBeforeFirst()){
                        Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                        alert.setTitle("奖学金信息不存在!");
                        alert.setHeaderText("不存该编号对应的奖学金"); // 设置对话框的头部文本
                        alert.setContentText("请检查奖学金编号是否正确");
                        alert.setResizable(false);
                        alert.show();
                    }
                    else{
                        rs=stuScholarDao.query_table(con,stu_id,this.scholar_text.getText(),"");
                        if(rs.isBeforeFirst()){
                            Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                            alert.setTitle("奖学金信息已存在!");
                            alert.setHeaderText("已存在该学号的奖学金信息"); // 设置对话框的头部文本
                            alert.setContentText("无须重复添加");
                            alert.setResizable(false);
                            alert.show();
                        }else{
                            int n=stuScholarDao.add(con,stu_id,scholar_id,"审核通过","未发放");
                            if(n==1){
                                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("学生获奖信息添加成功");
                                alert.setHeaderText("学生获奖记录添加成功!");
                                alert.setResizable(false);
                                // 设置对话框的内容文本
                                alert.show(); // 显示对话框
                                this.stu_text.setText("");
                                this.scholar_text.setText("");
                            }
                            else {
                                Alert alert=new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("学生获奖信息添加失败");
                                alert.setHeaderText("学生获奖记录添加失败!");
                                alert.setResizable(false);
                                // 设置对话框的内容文本
                                alert.show(); // 显示对话框
                                this.stu_text.setText("");
                                this.scholar_text.setText("");
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    Parent createNode() throws IOException {
        return FXMLLoader.load(getClass().getResource("add_stu_scholar.fxml"));
    }
}
