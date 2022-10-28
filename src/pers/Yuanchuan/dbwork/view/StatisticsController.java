package pers.Yuanchuan.dbwork.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import pers.Yuanchuan.dbwork.dao.StuScholarDao;
import pers.Yuanchuan.dbwork.model.Stu_Scholar;
import pers.Yuanchuan.dbwork.model.Student;
import pers.Yuanchuan.dbwork.utils.Db_utils;
import pers.Yuanchuan.dbwork.model.xs;
import pers.Yuanchuan.dbwork.model.tj;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class StatisticsController {

    @FXML
    private TableView<xs> xs_tab;

    @FXML
    private TableView<tj> tj_tab;


    @FXML
    private TableColumn<Stu_Scholar, String> id_col;

    @FXML
    private TableColumn<Stu_Scholar, String> name_col;

    @FXML
    private TableColumn<Stu_Scholar, String> major_col;

    @FXML
    private TableColumn<Stu_Scholar, String> sum_col;

    @FXML
    private TableColumn<Stu_Scholar, String> major1_col;

    @FXML
    private TableColumn<Stu_Scholar, String> sum1_col;

    @FXML
    private TextField id_text;

    @FXML
    private TextField name_text;

    @FXML
    private RadioButton accurate;

    @FXML
    private RadioButton vague;

    @FXML
    private AnchorPane tab_pane;

    @FXML
    private AnchorPane tab_pane1;

    @FXML
    private AnchorPane query_pane;

    private Db_utils dbUtils=new Db_utils();
    private StuScholarDao stuScholarDao=new StuScholarDao();

    @FXML
    void query_action(ActionEvent event) {
        Connection con=null;
        try {
            con=dbUtils.getCon();
            if(this.accurate.isSelected()){
                ResultSet rs=stuScholarDao.query_xs(con);
                fill_table_xs(rs);
            }else if(this.vague.isSelected()){
                ResultSet rs=stuScholarDao.query_tj(con);
                fill_table_tj(rs);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR); // 创建一个消息对话框
                alert.setTitle("查询失败!");
                alert.setHeaderText("请选择您的统计类型!"); // 设置对话框的头部文本
                alert.setResizable(false);
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fill_table_xs(ResultSet rs) throws Exception{
        ObservableList<xs> data= FXCollections.observableArrayList();
        while(rs.next()){
            xs in=new xs(rs.getString("id"),rs.getString("name"),
                    rs.getString("major"),rs.getInt("sum"));
            data.add(in);
        }
        id_col.setCellValueFactory(new PropertyValueFactory<>("stuId"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("stuName"));
        major_col.setCellValueFactory(new PropertyValueFactory<>("stuMajor"));
        sum_col.setCellValueFactory(new PropertyValueFactory<>("sum"));
        xs_tab.setItems(data);
        show_tab();
    }
    private void fill_table_tj(ResultSet rs) throws Exception{
        ObservableList<tj> data= FXCollections.observableArrayList();
        while(rs.next()){
            tj in=new tj(rs.getString("major"),rs.getInt("sum"));
            data.add(in);
        }
        major1_col.setCellValueFactory(new PropertyValueFactory<>("stuMajor"));
        sum1_col.setCellValueFactory(new PropertyValueFactory<>("sum"));
        tj_tab.setItems(data);
        show_tab1();
    }

    private void show_tab() {
        query_pane.setVisible(false);
        tab_pane.toFront();
        tab_pane.setVisible(true);
    }
    private void show_tab1() {
        query_pane.setVisible(false);
        tab_pane1.toFront();
        tab_pane1.setVisible(true);
    }

    private void show_query(){
        tab_pane.setVisible(false);
        tab_pane1.setVisible(false);
        query_pane.toFront();
        query_pane.setVisible(true);
    }


    @FXML
    void return_action(ActionEvent event) {
        show_query();
    }

    Parent createNode() throws IOException {
        return FXMLLoader.load(getClass().getResource("statistics.fxml"));
    }

}
