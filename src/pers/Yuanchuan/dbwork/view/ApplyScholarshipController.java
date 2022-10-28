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
import pers.Yuanchuan.dbwork.dao.ScholarshipDao;
import pers.Yuanchuan.dbwork.dao.StuScholarDao;
import pers.Yuanchuan.dbwork.model.Scholarship;
import pers.Yuanchuan.dbwork.model.Stu_Scholar;
import pers.Yuanchuan.dbwork.model.Student;
import pers.Yuanchuan.dbwork.utils.Db_utils;
import pers.Yuanchuan.dbwork.utils.String_isEmpty;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class ApplyScholarshipController {


    @FXML
    private AnchorPane tab_pane;

    @FXML
    private AnchorPane query_pane;

    @FXML
    private TableView<Scholarship> scholar_tab;

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
    private TextField stu_text;

    @FXML
    private TextField scholar_text;

    private Db_utils dbUtils=new Db_utils();
    private StuScholarDao stuScholarDao=new StuScholarDao();

    private String exam="待审核";
    private String state="未发放";

    private ScholarshipDao scholarshipDao=new ScholarshipDao();

    @FXML
    void no_action(ActionEvent event) {
        scholar_text.setText("");

    }


    @FXML
    void yes_action(ActionEvent event) {
        if(String_isEmpty.isEmpty(this.scholar_text.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
            alert.setTitle("好像出了点问题...");
            alert.setHeaderText("奖学金编号不能为空!"); // 设置对话框的头部文本
            alert.setContentText("请输入奖学金编号");
            alert.setResizable(false);
            // 设置对话框的内容文本
            alert.show(); // 显示对话框
        }
        else{
            String stu_id=LoginController.account;
            Integer scholar_id=Integer.parseInt(this.scholar_text.getText());
            Connection con=null;
            try {
                Scholarship scholarship=new Scholarship(scholar_id,"","",0,"");
                con=dbUtils.getCon();
                ResultSet rs=scholarshipDao.query(con,scholarship);
                if(!rs.isBeforeFirst()){
                    Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                    alert.setTitle("该奖学金不存在!");
                    alert.setHeaderText("不存该奖学金编号对应的奖学金"); // 设置对话框的头部文本
                    alert.setContentText("请确定奖学金编号是否正确");
                    alert.setResizable(false);
                    alert.show();
                }else {
                    ResultSet rs1=stuScholarDao.query_table(con,stu_id,this.scholar_text.getText(),"");
                    if(rs1.isBeforeFirst()){
                        Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                        alert.setTitle("您已经申请过该奖学金了!");
                        alert.setHeaderText("已存在该学号的奖学金申请信息"); // 设置对话框的头部文本
                        alert.setContentText("请确定输入的奖学金编号是否有误");
                        alert.setResizable(false);
                        alert.show();
                    }else{
                        fill_table(rs);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void fill_table(ResultSet rs) throws Exception{
        ObservableList<Scholarship> data= FXCollections.observableArrayList();
        while(rs.next()){
            Scholarship in=new Scholarship(rs.getInt("Scholar_id"),rs.getString("Scholar_name"),
                    rs.getString("Scholar_rank"),rs.getInt("Scholar_year"),
                    rs.getString("Scholar_i" +
                            "ssuer"));
            data.add(in);
        }
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("scholarName"));
        rank_col.setCellValueFactory(new PropertyValueFactory<>("scholarRank"));
        year_col.setCellValueFactory(new PropertyValueFactory<>("scholarYear"));
        issuer_col.setCellValueFactory(new PropertyValueFactory<>("scholarIssuer"));
        scholar_tab.setItems(data);
        show_tab();
    }

    private void show_tab() {
        query_pane.setVisible(false);
        query_pane.toBack();
        tab_pane.setVisible(true);
    }

    private void show_query(){
        tab_pane.setVisible(false);
        tab_pane.toBack();
        query_pane.setVisible(true);
    }

    @FXML
    void return_event(ActionEvent event) {
        this.scholar_text.setText("");
        show_query();
    }

    @FXML
    void add_event(ActionEvent event) throws Exception {
        Connection con=null;
        con=dbUtils.getCon();
        String stu_id=LoginController.account;
        Integer scholar_id=Integer.parseInt(this.scholar_text.getText());
        Scholarship scholarship=new Scholarship(scholar_id,"","",0,"");

        int n=stuScholarDao.add(con,stu_id,scholar_id,exam,state);
        if(n==1){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("学生获奖申请成功");
            alert.setHeaderText("学生获奖申请成功!");
            alert.setContentText("请耐心等待管理员的审核");
            alert.setResizable(false);
            // 设置对话框的内容文本
            alert.show(); // 显示对话框
        }
        else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("学生获奖信息添加失败");
            alert.setHeaderText("学生获奖记录添加失败!");
            alert.setContentText("请联系管理员！");
            alert.setResizable(false);
            // 设置对话框的内容文本
            alert.show(); // 显示对话框
            this.stu_text.setText("");
            this.scholar_text.setText("");
        }
        return_event(event);
    }

    Parent createNode() throws IOException {
        return FXMLLoader.load(getClass().getResource("apply_scholarship1.fxml"));
    }
}
