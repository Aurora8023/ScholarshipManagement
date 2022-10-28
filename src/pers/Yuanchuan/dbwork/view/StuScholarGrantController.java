package pers.Yuanchuan.dbwork.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import pers.Yuanchuan.dbwork.dao.StuScholarDao;
import pers.Yuanchuan.dbwork.model.Stu_Scholar;
import pers.Yuanchuan.dbwork.model.Student;
import pers.Yuanchuan.dbwork.utils.Db_utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class StuScholarGrantController {

    @FXML
    private TableView<Stu_Scholar> stu_scholar_tab;

    @FXML
    private TableColumn<Stu_Scholar, String> id_col;

    @FXML
    private TableColumn<Stu_Scholar, String> name_col;

    @FXML
    private TableColumn<Stu_Scholar, String> major_col;

    @FXML
    private TableColumn<Stu_Scholar, String> scholar_col;

    @FXML
    private TableColumn<Stu_Scholar, String> exam_col;

    @FXML
    private TableColumn<Stu_Scholar, String> state_col;

    @FXML
    private TextField id_text;

    @FXML
    private TextField name_text;

    @FXML
    private AnchorPane tab_pane;

    @FXML
    private AnchorPane query_pane;

    @FXML
    private TextField modify_scholar_text;

    private Db_utils dbUtils=new Db_utils();
    private StuScholarDao stuScholarDao=new StuScholarDao();

    @FXML
    void query_action(ActionEvent event) {
        String sid=this.modify_scholar_text.getText();
        Connection con=null;
        try {
            con=dbUtils.getCon();
            ResultSet rs = stuScholarDao.query_grant3(con,sid);
            if(!rs.isBeforeFirst()){
                Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                alert.setTitle("查询失败!");
                alert.setHeaderText("该编号不存在对应的奖学金"); // 设置对话框的头部文本
                alert.setContentText("请检查后重新尝试!");
                alert.setResizable(false);
                alert.show();
            }else{
                rs=stuScholarDao.query_grant1(con,sid);
                if(rs.isBeforeFirst()){
                    Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                    alert.setTitle("查询失败!");
                    alert.setHeaderText("该奖学金暂存在待审核的申请"); // 设置对话框的头部文本
                    alert.setContentText("请审核后重新尝试!");
                    alert.setResizable(false);
                    alert.show();
                }else{
                    rs=stuScholarDao.query_grant2(con,sid);
                    if(!rs.isBeforeFirst()){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
                        alert.setTitle("无需审核!");
                        alert.setHeaderText("该奖学金已全部发放!"); // 设置对话框的头部文本
                        alert.setResizable(false);
                        alert.show();
                    }else {
                        rs=stuScholarDao.query_grant(con,sid);
                        if(!rs.isBeforeFirst()){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
                            alert.setTitle("无需审核!");
                            alert.setHeaderText("该奖学金已全部发放!"); // 设置对话框的头部文本
                            alert.setResizable(false);
                            alert.show();
                        }else {
                            fill_table(rs);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void set_action(ActionEvent event) {
        String sid=this.modify_scholar_text.getText();
        Connection con=null;
        try {
            con=dbUtils.getCon();
            ResultSet rs=stuScholarDao.query_grant(con,sid);
            if(!rs.isBeforeFirst()){
                Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                alert.setTitle("提交失败!");
                alert.setHeaderText("当前暂无可修改的发放状态"); // 设置对话框的头部文本
                alert.setContentText("请稍后重新尝试");
                alert.setResizable(false);
                alert.show();
            }
            else{
                stuScholarDao.set_grant(con,sid);
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
                alert.setTitle("提交成功!");
                alert.setHeaderText("发放状态已修改为:已发放"); // 设置对话框的头部文本
//                alert.setContentText("请确定学生学号是否正确");
                alert.setResizable(false);
                alert.show();
                this.tab_pane.setVisible(false);
                this.query_pane.setVisible(true);
                this.query_pane.toFront();
                this.modify_scholar_text.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fill_table(ResultSet rs) throws Exception{
        ObservableList<Stu_Scholar> data= FXCollections.observableArrayList();
        while(rs.next()){
            Stu_Scholar in=new Stu_Scholar(rs.getString("id"),rs.getString("name"),
                    rs.getString("major"),rs.getString("sname"),rs.getString("exam"),rs.getString("state"));
            data.add(in);
        }
        id_col.setCellValueFactory(new PropertyValueFactory<>("stuId"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("stuName"));
        major_col.setCellValueFactory(new PropertyValueFactory<>("stuMajor"));
        scholar_col.setCellValueFactory(new PropertyValueFactory<>("scholarName"));
        exam_col.setCellValueFactory(new PropertyValueFactory<>("exam"));
        state_col.setCellValueFactory(new PropertyValueFactory<>("state"));
        stu_scholar_tab.setItems(data);
        show_tab();
    }

    private void show_tab() {
        query_pane.setVisible(false);
        tab_pane.toFront();
        tab_pane.setVisible(true);
    }

    private void show_query(){
        tab_pane.setVisible(false);
        query_pane.toFront();
        query_pane.setVisible(true);
    }



    @FXML
    void return_action(ActionEvent event) {
        this.modify_scholar_text.setText("");
        show_query();
    }

    Parent createNode() throws IOException {
        return FXMLLoader.load(getClass().getResource("stu_scholar_grant.fxml"));
    }

}
