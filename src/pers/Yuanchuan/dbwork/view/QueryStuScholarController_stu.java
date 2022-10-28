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
import pers.Yuanchuan.dbwork.model.Students;
import pers.Yuanchuan.dbwork.utils.Db_utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class QueryStuScholarController_stu {

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
    private TableColumn<Stu_Scholar,String> exam_col;

    @FXML
    private TableColumn<Stu_Scholar, String> state_col;


    @FXML
    private TextField password_text;

    @FXML
    private AnchorPane tab_pane;


    @FXML
    private AnchorPane query_pane;

    private Db_utils dbUtils=new Db_utils();
    private StuScholarDao stuScholarDao=new StuScholarDao();


    @FXML
    void query_action(ActionEvent event) {


        if(!this.password_text.getText().equals(UpdatePasswordController.password)){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("身份验证失败");
            alert.setHeaderText("密码有误!"); // 设置对话框的头部文本
            alert.setContentText("请检查后重新输入!");
            alert.setResizable(false);
            // 设置对话框的内容文本
            alert.show(); // 显示对话框
        }else {
            String id=LoginController.account;
            Student student=new Student(id,"","",0,"","","","","","");
            Connection con=null;
            try {
                con=dbUtils.getCon();
                ResultSet rs=stuScholarDao.query(con,student);
                fill_table(rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void fill_table(ResultSet rs) throws Exception{
        ObservableList<Stu_Scholar> data= FXCollections.observableArrayList();
        while(rs.next()){
            Stu_Scholar in=new Stu_Scholar(rs.getString("Stu_id"),rs.getString("Stu_name"),
                    rs.getString("Major_name"),rs.getString("Scholar_name"),rs.getString("exam"),rs.getString("state"));
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
    void reset_action(ActionEvent event) {
        this.password_text.setText("");
    }


    @FXML
    void return_action(ActionEvent event) {
        this.password_text.setText("");
        show_query();
    }

    Parent createNode() throws IOException {
        return FXMLLoader.load(getClass().getResource("query_stu1_scholar.fxml"));
    }

}
