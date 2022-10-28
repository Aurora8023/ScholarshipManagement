package pers.Yuanchuan.dbwork.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFrameStart_stu extends Application {


    @Override
    public void start(Stage stage)throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("main_frame_stu.fxml"));
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("奖学金发放管理系统——学生端");
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }

}
