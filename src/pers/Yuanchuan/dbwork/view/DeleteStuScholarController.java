package pers.Yuanchuan.dbwork.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pers.Yuanchuan.dbwork.dao.StuScholarDao;
import pers.Yuanchuan.dbwork.model.Stu_Scholar;
import pers.Yuanchuan.dbwork.utils.Db_utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;


public class DeleteStuScholarController {

    @FXML
    private TextField del_stu_text;

    @FXML
    private TextField del_scholar_text;

    @FXML
    private TableView<Stu_Scholar> del_tab;

    @FXML
    private TableColumn<Stu_Scholar, String> id_col;

    @FXML
    private TableColumn<Stu_Scholar, String> name_col;

    @FXML
    private TableColumn<Stu_Scholar, String> major_col;

    @FXML
    private TableColumn<Stu_Scholar, String> scholar_name_col;

    @FXML
    private TableColumn<Stu_Scholar, String> exam_col;

    @FXML
    private TableColumn<Stu_Scholar, String> state_col;

    @FXML
    private Button yes_button;

    @FXML
    private Button no_button;

    private Db_utils dbUtils=new Db_utils();
    private StuScholarDao stuScholarDao=new StuScholarDao();
    private String stu_name;
    private String scholar_name;


    @FXML
    void no_action(ActionEvent event) {
        is_vis(false);
        del_stu_text.setText("");
        del_scholar_text.setText("");
    }

    private void is_vis(boolean b) {
        del_tab.setVisible(b);
        yes_button.setVisible(b);
        no_button.setVisible(b);
    }

    @FXML
    void query_action(ActionEvent event) {
        String stu_id=del_stu_text.getText();
        String scholar_id=del_scholar_text.getText();
        Connection con=null;
        try {
            if(this.del_stu_text.getText().equals("")||this.del_scholar_text.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                alert.setTitle("请输入完整的信息!");
                alert.setHeaderText("您输入的信息不完整"); // 设置对话框的头部文本
                alert.setContentText("请输入完整的学号和奖学金编号");
                alert.setResizable(false);
                alert.show();
            }else {
                con = dbUtils.getCon();
                ResultSet rs = stuScholarDao.query_del(con, stu_id, scholar_id);
                if(!rs.isBeforeFirst()){
                    Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                    alert.setTitle("信息有误!");
                    alert.setHeaderText("输入的学号或奖学金编号有误"); // 设置对话框的头部文本
                    alert.setContentText("不存在对应的获奖信息!");
                    alert.setResizable(false);
                    alert.show();
                }else {
                    fill_table(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fill_table(ResultSet rs) throws Exception{
        ObservableList<Stu_Scholar> data= FXCollections.observableArrayList();
        while(rs.next()){
            Stu_Scholar in=new Stu_Scholar(rs.getString("Stu_id"),rs.getString("Stu_name"),
                    rs.getString("Major_name"),rs.getString("Scholar_name"),rs.getString("exam"),rs.getString("state"));
            stu_name=rs.getString("Stu_name");
            scholar_name=rs.getString("Scholar_name");
            data.add(in);
        }
        id_col.setCellValueFactory(new PropertyValueFactory<>("stuId"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("stuName"));
        major_col.setCellValueFactory(new PropertyValueFactory<>("stuMajor"));
        scholar_name_col.setCellValueFactory(new PropertyValueFactory<>("scholarName"));
        exam_col.setCellValueFactory(new PropertyValueFactory<>("exam"));
        state_col.setCellValueFactory(new PropertyValueFactory<>("state"));
        del_tab.setItems(data);
        show_tab();
    }

    private void show_tab() {
        is_vis(true);
    }

    @FXML
    void yes_action(ActionEvent event) {
        Connection con=null;
        try {
                con=dbUtils.getCon();
                int n=stuScholarDao.delete(con,del_stu_text.getText(),del_scholar_text.getText());
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("学生获奖信息删除成功");
                alert.setHeaderText(stu_name+"同学的“"+scholar_name+"获奖信息删除成功!"); // 设置对话框的头部文本
                alert.setResizable(false);
                // 设置对话框的内容文本
                alert.show(); // 显示对话框
                this.del_stu_text.setText("");
                this.del_scholar_text.setText("");
                del_tab.getItems().clear();
                del_tab.refresh();
                del_tab.setVisible(false);
                yes_button.setVisible(false);
                no_button.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Parent createNode() throws IOException {
        return FXMLLoader.load(getClass().getResource("delete_stu_scholar.fxml"));
    }
}
