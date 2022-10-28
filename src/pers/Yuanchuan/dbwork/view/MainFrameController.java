package pers.Yuanchuan.dbwork.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class MainFrameController {

    @FXML
    private BorderPane rootpane;

    @FXML
    void add_scholarship_event(ActionEvent event) {
        try {
            rootpane.setCenter(new AddScholarController().createNode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void query_scholarship_event(ActionEvent event) {
        try {
            rootpane.setCenter(new QueryScholarshipController().createNode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void update_scholar_event(ActionEvent event) {
        try{
            rootpane.setCenter(new UpdateScholarshipController().createNode());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void query_stu_scholar_event(ActionEvent event) {
        try{
            rootpane.setCenter(new QueryStuScholarController().createNode());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void query_stu1_scholar_event(ActionEvent event) {
        try{
            rootpane.setCenter(new QueryStuScholarController_stu().createNode());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void add_stu_scholar_event(ActionEvent event) {
        try{
            rootpane.setCenter(new AddStuScholarController().createNode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void delete_stu_scholar_event(ActionEvent event) {
        try{
            rootpane.setCenter(new DeleteStuScholarController().createNode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void update_stu_scholar_event(ActionEvent event) {
        try{
            rootpane.setCenter(new UpdateStuScholarController().createNode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void delete_scholar_event(ActionEvent event) {
        try{
            rootpane.setCenter(new DeleteScholarshipController().createNode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void add_student_event(ActionEvent event) {
        try{
            rootpane.setCenter(new AddStudentController().createNode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void update_stu_event(ActionEvent event) {
        try{
            rootpane.setCenter(new UpdateStudentController().createNode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void delete_student_event(ActionEvent event) {
        try{
            rootpane.setCenter(new DeleteStudentController().createNode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void report_export_event(ActionEvent event) {
        try {
            rootpane.setCenter(new ReportExportController().createNode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void update_password_event(ActionEvent event){
        try{
            rootpane.setCenter(new UpdatePasswordController().createNode());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void query_stu_basic_event(ActionEvent event){
        try{
            rootpane.setCenter(new QueryStuBasicController().createNode());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void apply_scholarship_event(ActionEvent event){
        try{
            rootpane.setCenter(new ApplyScholarshipController().createNode());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void examine_scholarship_event(ActionEvent event) {
        try {
            rootpane.setCenter(new ExamineScholarshipController().createNode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void stu_scholar_grant_event(ActionEvent event){
        try {
            rootpane.setCenter(new StuScholarGrantController().createNode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void statistics_event(ActionEvent event) {
        try {
            rootpane.setCenter(new StatisticsController().createNode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
