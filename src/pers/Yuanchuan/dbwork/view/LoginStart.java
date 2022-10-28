package pers.Yuanchuan.dbwork.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginStart extends Application {


    public static int random = (int)(Math.random()*9);

    @Override
    public void start(Stage stage)throws Exception{
        String name = "login";
        name+=random;
        name+=".fxml";
        Parent root= FXMLLoader.load(getClass().getResource(name));
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("用户登录");
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }

}
