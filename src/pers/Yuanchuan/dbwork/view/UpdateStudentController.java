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
import pers.Yuanchuan.dbwork.dao.StudentDao;
import pers.Yuanchuan.dbwork.model.Student;
import pers.Yuanchuan.dbwork.model.Student;
import pers.Yuanchuan.dbwork.utils.Db_utils;
import pers.Yuanchuan.dbwork.utils.String_isEmpty;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class UpdateStudentController {

    @FXML
    private AnchorPane result_pane;

    @FXML
    private TableView<Student> result_tab;

    @FXML
    private TableColumn<Student, String> id_col;

    @FXML
    private TableColumn<Student, String> name_col;

    @FXML
    private TableColumn<Student, String> sex_col;

    @FXML
    private TableColumn<Student, String> birth_col;

    @FXML
    private TableColumn<Student, Integer> grade_col;

    @FXML
    private TableColumn<Student, String> major_col;

    @FXML
    private TableColumn<Student, String> card_col;

    @FXML
    private AnchorPane modify_pane;

    @FXML
    private TextField stu_id_text;

    @FXML
    private TextField stu_name_text;

    @FXML
    private TextField stu_year_text;


    @FXML
    private TextField stu_idcard_text;

    @FXML
    private TextField stu_khyh_text;

    @FXML
    private TextField stu_yhkh_text;

    @FXML
    private TextField stu_class_text;

    @FXML
    private TextField stu_major_text;

    @FXML
    private RadioButton man;

    @FXML
    private RadioButton woman;

    @FXML
    private AnchorPane main_pane;

    @FXML
    private TextField modify_id_text;

    @FXML
    private RadioButton bk;

    @FXML
    private RadioButton zk;

    @FXML
    private RadioButton yjs;

    private Db_utils dbUtils=new Db_utils();
    private Student update_stu;
    private StudentDao studentDao=new StudentDao();

    @FXML
    void modify_action(ActionEvent event) {
        if(String_isEmpty.isEmpty(this.stu_name_text.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
            alert.setTitle("好像出了点问题...");
            alert.setHeaderText("学生姓名不能为空!"); // 设置对话框的头部文本
            alert.setContentText("请重新修改后再提交");
            alert.setResizable(false);
            // 设置对话框的内容文本
            alert.show(); // 显示对话框
        }else{
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
                update_stu.setName(this.stu_name_text.getText());
                update_stu.setYear(Integer.parseInt(this.stu_year_text.getText()));
                update_stu.setIdcard(this.stu_idcard_text.getText());
                update_stu.setKhyh(this.stu_khyh_text.getText());
                update_stu.setYhkh(this.stu_yhkh_text.getText());
                update_stu.setBanji(this.stu_class_text.getText());
                update_stu.setMajor(this.stu_major_text.getText());
                Connection con=null;
                try{
                    con=dbUtils.getCon();
                    ResultSet rs=studentDao.major(con,update_stu);
                    if(!rs.isBeforeFirst()){
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("学生信息添加失败");
                        alert.setHeaderText("专业号输入有误!"); // 设置对话框的头部文本
                        alert.setContentText("不存在该专业号对应的专业!"); // 设置对话框的头部文本
                        alert.setResizable(false);
                        // 设置对话框的内容文本
                        alert.show(); // 显示对话框
                    }else{
                        int n=studentDao.update(con,update_stu);
                        if(n==1){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
                            alert.setTitle("学生信息修改成功!");
                            alert.setHeaderText(update_stu.getName()+" 同学的信息修改成功!"); // 设置对话框的头部文本
                            alert.setResizable(false);
                            alert.show();
//                modify_result(update_stu);
                        }else{
                            Alert alert = new Alert(Alert.AlertType.ERROR); // 创建一个消息对话框
                            alert.setTitle("学生信息修改失败!");
                            alert.setHeaderText("很抱歉,学生信息修改失败!"); // 设置对话框的头部文本
                            alert.setResizable(false);
                            alert.show();
                        }
                        reset_action(event);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

//    private void modify_result(Student update_stu) {
//        ObservableList<Student> data= FXCollections.observableArrayList();
//        data.add(update_stu);
//        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
//        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
//        sex_col.setCellValueFactory(new PropertyValueFactory<>("sex"));
//        birth_col.setCellValueFactory(new PropertyValueFactory<>("birth"));
//        grade_col.setCellValueFactory(new PropertyValueFactory<>("grade"));
//        major_col.setCellValueFactory(new PropertyValueFactory<>("m_id"));
//        card_col.setCellValueFactory(new PropertyValueFactory<>("card"));
//        result_tab.setItems(data);
//
//        main_pane.setVisible(false);
//        modify_pane.setVisible(false);
//        result_pane.setVisible(true);
//        result_pane.toFront();
//    }

    @FXML
    void query_action(ActionEvent event) {
        String id=null;
        if(String_isEmpty.isNotEmpty(this.modify_id_text.getText())){
            id=this.modify_id_text.getText();
        }
        Connection con=null;
        try {
            if(this.modify_id_text.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR); // 创建一个消息对话框
                alert.setTitle("好像出了点问题...");
                alert.setHeaderText("学号不能为空!"); // 设置对话框的头部文本
                alert.setContentText("请输入学号后重新尝试");
                alert.setResizable(false);
                // 设置对话框的内容文本
                alert.show(); // 显示对话框
            }else {
                con=dbUtils.getCon();
                ResultSet rs=studentDao.query(con,id);
                if(rs.isBeforeFirst()){
                    while(rs.next()){
                        update_stu=new Student(rs.getString("Stu_id"),rs.getString("Stu_name"),
                                rs.getString("Stu_sex"),rs.getInt("Stu_year"),
                                rs.getString("Stu_type"),rs.getString("Stu_idcard"),
                                rs.getString("Stu_khyh"),rs.getString("Stu_yhkh"),
                                rs.getString("Stu_class"),rs.getString("Major_id"));
                        fill_text(update_stu);
                        modify_pane.setVisible(true);
                        modify_pane.toFront();
                        main_pane.setVisible(false);
                    }
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

    private void fill_text(Student update_stu) {
        this.stu_id_text.setText(update_stu.getId());
        this.stu_name_text.setText(update_stu.getName());
        /*
        性别
         */
        if(update_stu.getSex().equals("男")){
            this.man.setSelected(true);
        }else{
            this.woman.setSelected(true);
        }
        this.stu_year_text.setText(String.valueOf(update_stu.getYear()));

        if(update_stu.getType().equals("专科")){
            this.zk.setSelected(true);
        }else if(update_stu.getType().equals("本科")){
            this.bk.setSelected(true);
        }else{
            this.yjs.setSelected(true);
        }
        this.stu_idcard_text.setText(update_stu.getIdcard());
        this.stu_khyh_text.setText(update_stu.getKhyh());
        this.stu_yhkh_text.setText(update_stu.getYhkh());
        this.stu_class_text.setText(update_stu.getBanji());
        this.stu_major_text.setText(update_stu.getMajor());
    }

    @FXML
    void reset_action(ActionEvent event) {
        modify_pane.setVisible(false);
        main_pane.toFront();
        main_pane.setVisible(true);
        this.modify_id_text.setText("");
    }

    @FXML
    void return_action(ActionEvent event) {
//        result_pane.setVisible(false);
//        main_pane.toFront();
//        main_pane.setVisible(true);
//        this.modify_id_text.setText("");
    }

    @FXML
    void man_action(ActionEvent event){
        update_stu.setSex("男");
    }
    @FXML
    void woman_action(ActionEvent event){
        update_stu.setSex("女");
    }

    @FXML
    void zk_action(ActionEvent event){
        update_stu.setType("专科");
    }
    @FXML
    void bk_action(ActionEvent event){
        update_stu.setType("本科");
    }
    @FXML
    void yjs_action(ActionEvent event){
        update_stu.setType("研究生");
    }


    Parent createNode() throws IOException {
        return FXMLLoader.load(getClass().getResource("update_student.fxml"));
    }
}
