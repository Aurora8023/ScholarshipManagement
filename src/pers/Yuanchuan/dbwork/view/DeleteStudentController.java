package pers.Yuanchuan.dbwork.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pers.Yuanchuan.dbwork.dao.StudentDao;
import pers.Yuanchuan.dbwork.model.Student;
import pers.Yuanchuan.dbwork.model.Students;
import pers.Yuanchuan.dbwork.utils.Db_utils;
import pers.Yuanchuan.dbwork.utils.String_isEmpty;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class DeleteStudentController {
    @FXML
    private TextField id_text;

    @FXML
    private TableView<Student> del_tab;

    @FXML
    private TableColumn<Students, String> id_col;

    @FXML
    private TableColumn<Students, String> name_col;

    @FXML
    private TableColumn<Students, String> banji_col;

    @FXML
    private TableColumn<Students, String> year_col;

    @FXML
    private TableColumn<Students, Integer> type_col;

    @FXML
    private TableColumn<Students, String> major_col;


    @FXML
    private Button del_button;

    @FXML
    private Button reset_button;

    private Student del_stu;
    private Db_utils dbUtils=new Db_utils();
    private StudentDao studentDao=new StudentDao();

    @FXML
    void delete_action(ActionEvent event) {
        Connection con=null;
        try {
            con=dbUtils.getCon();
            int n=studentDao.deleteuser(con,del_stu.getId());
            System.out.println(n);
            if(n!=0){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("学生信息删除成功");
                alert.setHeaderText(del_stu.getName()+" 同学的信息删除成功!"); // 设置对话框的头部文本
                alert.setResizable(false);
                // 设置对话框的内容文本
                alert.show(); // 显示对话框
                this.id_text.setText("");
                del_tab.getItems().clear();
                del_tab.refresh();
                tab_vis(false);
            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("学生信息删除失败");
                alert.setHeaderText(del_stu.getName()+" 同学的信息删除失败!"); // 设置对话框的头部文本
                alert.setResizable(false);
                // 设置对话框的内容文本
                alert.show(); // 显示对话框
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void query_action(ActionEvent event) {
        String id=null;
        if(String_isEmpty.isNotEmpty(this.id_text.getText())){
            id=this.id_text.getText();
        }
        Connection con=null;
        try {
            if(this.id_text.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR); // 创建一个消息对话框
                alert.setTitle("好像出了点问题...");
                alert.setHeaderText("学号不能为空!"); // 设置对话框的头部文本
                alert.setContentText("请输入学号后重新尝试");
                alert.setResizable(false);
                // 设置对话框的内容文本
                alert.show(); // 显示对话框
            }
            else {
                con=dbUtils.getCon();
                ResultSet rs=studentDao.query(con,id);
                if(rs.isBeforeFirst()){
                    fill_table(rs);
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                    alert.setTitle("该学生信息不存在!");
                    alert.setHeaderText("不存该学号对应的学生"); // 设置对话框的头部文本
                    alert.setContentText("请确定学生学号是否正确");
                    alert.setResizable(false);
                    alert.show();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fill_table(ResultSet rs) throws Exception{
        ObservableList<Student> data= FXCollections.observableArrayList();
        while(rs.next()){
            Student in=new Student(rs.getString("Stu_id"),rs.getString("Stu_name"),
                    rs.getString("Stu_sex"),rs.getInt("Stu_year"),
                    rs.getString("Stu_type"),rs.getString("Stu_idcard"),
                    rs.getString("Stu_khyh"),rs.getString("Stu_yhkh"),
                    rs.getString("Stu_class"),rs.getString("Major_id"));
            del_stu=in;
            data.add(in);
        }
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        banji_col.setCellValueFactory(new PropertyValueFactory<>("banji"));
        major_col.setCellValueFactory(new PropertyValueFactory<>("major"));
        year_col.setCellValueFactory(new PropertyValueFactory<>("year"));
        type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        del_tab.setItems(data);
        tab_vis(true);
    }

    private void tab_vis(boolean x) {
        if(x){
            del_tab.setVisible(true);
            del_button.setVisible(true);
            reset_button.setVisible(true);
        } else{
            del_tab.setVisible(false);
            del_button.setVisible(false);
            reset_button.setVisible(false);
        }
    }

    @FXML
    void reset_action(ActionEvent event) {
        this.id_text.setText("");
        tab_vis(false);
    }

    Parent createNode() throws IOException {
        return FXMLLoader.load(getClass().getResource("delete_student.fxml"));
    }
}
