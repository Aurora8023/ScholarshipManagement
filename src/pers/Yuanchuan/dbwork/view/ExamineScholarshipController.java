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
import pers.Yuanchuan.dbwork.dao.ScholarshipDao;
import pers.Yuanchuan.dbwork.dao.StuScholarDao;
import pers.Yuanchuan.dbwork.model.Scholarship;
import pers.Yuanchuan.dbwork.utils.Db_utils;
import pers.Yuanchuan.dbwork.utils.String_isEmpty;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class ExamineScholarshipController {

    @FXML
    private TextField stu_text;

    @FXML
    private TextField scholar_text;


    @FXML
    private AnchorPane main_pane;

    @FXML
    private AnchorPane modify_result_pane;

    @FXML
    private AnchorPane query_result_pane;

    @FXML
    private AnchorPane query_pane;
    @FXML
    private AnchorPane modify_pane;

    @FXML
    private TableView<Scholarship> update_tab;

    @FXML
    private TableColumn<Scholarship, Integer> id_col;

    @FXML
    private TableColumn<Scholarship, String> name_col;

    @FXML
    private TableColumn<Scholarship, String> rank_col;

    @FXML
    private TableColumn<Scholarship, Integer> year_col;

    @FXML
    private TableColumn<Scholarship, String> issuer_col;

    @FXML
    private TextField id_text;

    @FXML
    private TextField name_text;

    @FXML
    private TextField rank_text;

    @FXML
    private TextField year_text;

    @FXML
    private TextField issuer_text;

    @FXML
    private TextField modify_id_text;

    @FXML
    private TextField modify_scholar_text;

    @FXML
    private RadioButton country_level;

    @FXML
    private RadioButton province_level;

    @FXML
    private RadioButton school_level;


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

    private Scholarship update_scholar;
    private Db_utils dbUtils=new Db_utils();
    private StuScholarDao stuScholarDao=new StuScholarDao();

    private ScholarshipDao scholarshipDao=new ScholarshipDao();

    private String id = null;

    private String update_stu_id;
    private int update_scholar_id;

    private String update_exam=null;

    private String update_state=null;


    @FXML
    void modify_action(ActionEvent event) {
        id=this.modify_scholar_text.getText();
        this.main_pane.setVisible(false);
        this.query_result_pane.setVisible(false);
        this.query_pane.setVisible(true);
//        update_scholar.setScholarName(this.name_text.getText());
//        update_scholar.setScholarYear(Integer.parseInt(this.year_text.getText()));
//        update_scholar.setScholarIssuer(this.issuer_text.getText());
//        Connection con=null;
//        try{
//            con=dbUtils.getCon();
//            int n=scholarshipDao.update(con,update_scholar);
//            if(n==1){
//                Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
//                alert.setTitle("奖学金信息修改成功!");
//                alert.setHeaderText(update_scholar.getScholarName()+"修改成功!"); // 设置对话框的头部文本
//                alert.setResizable(false);
//                alert.show();
//            }else{
//                Alert alert = new Alert(Alert.AlertType.ERROR); // 创建一个消息对话框
//                alert.setTitle("奖学金信息修改失败!");
//                alert.setHeaderText("很抱歉,奖学金修改失败!"); // 设置对话框的头部文本
//                alert.setResizable(false);
//                alert.show();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

//    private void modify_result(Scholarship update_scholar) {
//        ObservableList<Scholarship> data= FXCollections.observableArrayList();
//        data.add(update_scholar);
//        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
//        name_col.setCellValueFactory(new PropertyValueFactory<>("scholarName"));
//        rank_col.setCellValueFactory(new PropertyValueFactory<>("scholarRank"));
//        year_col.setCellValueFactory(new PropertyValueFactory<>("scholarYear"));
//        issuer_col.setCellValueFactory(new PropertyValueFactory<>("scholarIssuer"));
//        update_tab.setItems(data);
//
//        main_pane.setVisible(false);
//        query_result_pane.setVisible(false);
//        modify_result_pane.setVisible(true);
//        modify_result_pane.toFront();
//    }



    /*
    复制开始
     */
    @FXML
    void query_event(ActionEvent event) {
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
                alert.setContentText("请输入学号后重新查询");
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
                        if(update_exam.equals("待审核")){
                            fill_text(update_stu_id,update_scholar_id,update_exam,update_state);
                            modify_pane.setVisible(true);
                            modify_pane.toFront();
                        }else{
                            Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                            alert.setTitle("该学生申请信息已经审核!");
                            alert.setHeaderText("该学号学生的申请信息已经审核"); // 设置对话框的头部文本
                            alert.setContentText("请确定输入学号或奖学金编号是否正确");
                            alert.setResizable(false);
                            alert.show();
                        }
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

    @FXML
    void exit_action(ActionEvent event){
        this.query_pane.setVisible(false);
        this.main_pane.setVisible(true);
        main_pane.toFront();
        this.modify_id_text.setText("");
        this.modify_scholar_text.setText("");
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
                    alert.setTitle("学生申请处理成功!");
                    alert.setHeaderText("学号为:"+update_stu_id+"的同学的申请信息处理成功!"); // 设置对话框的头部文本
                    alert.setResizable(false);
                    alert.show();
                    this.modify_pane.setVisible(false);
                    this.modify_id_text.setText("");
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR); // 创建一个消息对话框
                    alert.setTitle("学生获奖信息修改失败!");
                    alert.setHeaderText("很抱歉,学生获奖信息修改失败!"); // 设置对话框的头部文本
                    alert.setResizable(false);
                    alert.show();
                }
//                no_event(event);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
    复制结束
     */

    @FXML
    void no_event(ActionEvent event) {
        modify_pane.setVisible(false);
        query_pane.toFront();
        modify_id_text.setText("");
//        modify_scholar_text.setText("");
    }

    @FXML
    void query_button(ActionEvent event) {
        int id=0;
        int year=0;
        if(String_isEmpty.isNotEmpty(this.modify_scholar_text.getText())){
            id=Integer.parseInt(this.modify_scholar_text.getText());
        }
        String name="";
        String level="";
        String issuer="";
        Scholarship scholarship=new Scholarship(id,name,level,year,issuer);
        Connection con=null;
        try {
            if(this.modify_scholar_text.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                alert.setTitle("请输入奖学金编号!");
                alert.setHeaderText("奖学金编号不能为空!"); // 设置对话框的头部文本
                alert.setContentText("请输入奖学金编号后再进行审核!");
                alert.setResizable(false);
                alert.show();
            }
            else{
                con=dbUtils.getCon();
                ResultSet rs=scholarshipDao.query(con,scholarship);
                if(rs.isBeforeFirst()){
                    while(rs.next()){
                        update_scholar=new Scholarship(rs.getInt("Scholar_id"),rs.getString("Scholar_name"),
                                rs.getString("Scholar_rank"),rs.getInt("Scholar_year"),
                                rs.getString("Scholar_issuer"));
                        fill_text(update_scholar);
                        query_result_pane.setVisible(true);
                        query_result_pane.toFront();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                    alert.setTitle("该奖学金不存在!");
                    alert.setHeaderText("不存该奖学金编号对应的奖学金"); // 设置对话框的头部文本
                    alert.setContentText("请确定奖学金编号是否正确");
                    alert.setResizable(false);
                    alert.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fill_text(Scholarship update_scholar) {
        this.id_text.setEditable(false);
        this.id_text.setText(String.valueOf(update_scholar.getId()));
        this.name_text.setText(update_scholar.getScholarName());
        if(update_scholar.getScholarRank().equals("国家级")){
            country_level.setSelected(true);
        }else if(update_scholar.getScholarRank().equals("省级")){
            province_level.setSelected(true);
        }else{
            school_level.setSelected(true);
        }

        this.year_text.setText(String.valueOf(update_scholar.getScholarYear()));
        this.issuer_text.setText(update_scholar.getScholarIssuer());
    }

    @FXML
    void reset_action(ActionEvent event) {
        query_result_pane.setVisible(false);
        main_pane.toFront();
        this.modify_scholar_text.setText("");
    }

    @FXML
    void return_action(ActionEvent event) {
        modify_result_pane.setVisible(false);
        main_pane.toFront();
        main_pane.setVisible(true);
        this.modify_scholar_text.setText("");
    }

    @FXML
    void country_level_action(ActionEvent event){
        update_scholar.setScholarRank("国家级");
    }

    @FXML
    void province_level_action(ActionEvent event){
        update_scholar.setScholarRank("省级");
    }

    @FXML
    void school_level_action(ActionEvent event){
        update_scholar.setScholarRank("校级");
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
        return FXMLLoader.load(getClass().getResource("ExamineScholarship.fxml"));
    }
}
