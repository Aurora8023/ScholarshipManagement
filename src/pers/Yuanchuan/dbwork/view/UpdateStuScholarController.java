package pers.Yuanchuan.dbwork.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import pers.Yuanchuan.dbwork.dao.StuScholarDao;
import pers.Yuanchuan.dbwork.utils.Db_utils;
import pers.Yuanchuan.dbwork.utils.String_isEmpty;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class UpdateStuScholarController {

    @FXML
    private TextField stu_text;

    @FXML
    private TextField scholar_text;

    @FXML
    private TextField exam_text;


    @FXML
    private TextField state_text;

    @FXML
    private TextField modify_scholar_text;

    @FXML
    private TextField modify_id_text;

    @FXML
    private TextField modify_exam_text;

    @FXML
    private TextField modify_state_text;

    @FXML
    private RadioButton dsh;

    @FXML
    private RadioButton shwtg;

    @FXML
    private RadioButton shtg;

    @FXML
    private RadioButton wff;

    @FXML
    private RadioButton yff;


    @FXML
    private AnchorPane query_pane;

    @FXML
    private AnchorPane modify_pane;

    private Db_utils dbUtils=new Db_utils();
    private StuScholarDao stuScholarDao=new StuScholarDao();
    private String update_stu_id;
    private int update_scholar_id;

    private String update_exam=null;

    private String update_state=null;


    @FXML
    void no_event(ActionEvent event) {
        modify_pane.setVisible(false);
        query_pane.toFront();
        modify_id_text.setText("");
        modify_scholar_text.setText("");
    }

    @FXML
    void query_event(ActionEvent event) {
        String id=null;
        String scholar_id=null;
        String exam=null;
        String state=null;
        if(String_isEmpty.isNotEmpty(this.modify_id_text.getText())){
            id=this.modify_id_text.getText();
        }
        if(String_isEmpty.isNotEmpty(this.modify_scholar_text.getText())){
            scholar_id=this.modify_scholar_text.getText();
        }

        Connection con=null;
        try {
            if(this.modify_id_text.getText().equals("")||this.modify_scholar_text.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                alert.setTitle("请输入完整的信息!");
                alert.setHeaderText("您输入的信息不完整"); // 设置对话框的头部文本
                alert.setContentText("请输入完整的学号和奖学金编号");
                alert.setResizable(false);
                alert.show();
            }
            else{
                con=dbUtils.getCon();
                ResultSet rs=stuScholarDao.query_table1(con,id,scholar_id, null, null);
                if(rs.isBeforeFirst()){
                    while(rs.next()){
                        update_stu_id=rs.getString("Stu_id");
                        update_scholar_id=rs.getInt("Scholar_id");
                        update_exam=rs.getString("exam");
                        update_state=rs.getString("state");
                        fill_text(update_stu_id,update_scholar_id,update_exam,update_state);
                        modify_pane.setVisible(true);
                        modify_pane.toFront();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                    alert.setTitle("该学生学号不存在获奖信息!");
                    alert.setHeaderText("不存该学号学生的获奖信息"); // 设置对话框的头部文本
                    alert.setContentText("请确定输入学号或奖学金编号是否正确");
                    alert.setResizable(false);
                    alert.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fill_text(String update_stu_id, int update_scholar_id,String update_exam,String update_state) {
        this.stu_text.setEditable(false);
        this.stu_text.setText(update_stu_id);
        this.scholar_text.setText(String.valueOf(update_scholar_id));


        if(update_exam.equals("审核通过")){
            this.shtg.setSelected(true);
        }else if(update_exam.equals("审核未通过")){
            this.shwtg.setSelected(true);
        }else{
            this.dsh.setSelected(true);
        }
        if (update_state.equals("已发放")){
            this.yff.setSelected(true);
        }else{
            this.wff.setSelected(true);
        }

    }

    @FXML
    void yes_event(ActionEvent event) {
        update_stu_id=this.stu_text.getText();
        update_scholar_id=Integer.parseInt(this.scholar_text.getText());

        Connection con=null;
        try{
            if(update_state.equals("已发放")&&!update_exam.equals("审核通过")){
                Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                alert.setTitle("学生信息修改失败!");
                alert.setHeaderText("学生奖学金若已发放，审核状态必须为“审核通过”"); // 设置对话框的头部文本
                alert.setResizable(false);
                alert.show();
            }else{
                con=dbUtils.getCon();
                int n=stuScholarDao.update(con,update_stu_id,update_scholar_id,update_exam,update_state);
                if(n==1){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
                    alert.setTitle("学生信息修改成功!");
                    alert.setHeaderText("学号为:"+update_stu_id+"的同学的获奖信息修改成功!"); // 设置对话框的头部文本
                    alert.setResizable(false);
                    alert.show();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR); // 创建一个消息对话框
                    alert.setTitle("学生获奖信息修改失败!");
                    alert.setHeaderText("很抱歉,学生获奖信息修改失败!"); // 设置对话框的头部文本
                    alert.setResizable(false);
                    alert.show();
                }
                no_event(event);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void dsh_action(ActionEvent event){
        update_exam="待审核";
    }
    @FXML
    void shtg_action(ActionEvent event){
        update_exam="审核通过";
    }
    @FXML
    void shwtg_action(ActionEvent event){
        update_exam="审核未通过";
    }
    @FXML
    void wff_action(ActionEvent event){
        update_state="未发放";
    }
    @FXML
    void yff_action(ActionEvent event){
        update_state="已发放";
    }



    Parent createNode() throws IOException {
        return FXMLLoader.load(getClass().getResource("update_stu_scholar.fxml"));
    }
}
