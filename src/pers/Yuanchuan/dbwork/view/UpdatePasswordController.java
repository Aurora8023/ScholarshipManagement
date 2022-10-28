package pers.Yuanchuan.dbwork.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import pers.Yuanchuan.dbwork.dao.StudentDao;
import pers.Yuanchuan.dbwork.model.User;
import pers.Yuanchuan.dbwork.utils.Db_utils;
import pers.Yuanchuan.dbwork.utils.MD5_Encryption;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;

public class UpdatePasswordController {

    public static String password=LoginController.password;

    @FXML
    private TextField a_password_text;

    @FXML
    private TextField b_password_text;

    @FXML
    private TextField c_password_text;

    private Db_utils dbUtils=new Db_utils();

    private StudentDao studentDao=new StudentDao();

    @FXML
    void modify_action(ActionEvent event) {
        if(!this.a_password_text.getText().equals(password)){
//            System.out.println(this.a_password_text.getText());//我们输入的错误的密码
//            System.out.println(password);//正确的密码
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("失败");
            alert.setHeaderText("修改失败!"); // 设置对话框的头部文本
            alert.setContentText("原密码有误");
            alert.setResizable(false);
            // 设置对话框的内容文本
            alert.show(); // 显示对话框
        }else if((!this.b_password_text.getText().equals(this.c_password_text.getText()))||(this.c_password_text.getText().length()<8)||(this.c_password_text.getText().length()>20)){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("失败");
            alert.setHeaderText("修改失败!"); // 设置对话框的头部文本
            alert.setContentText("两次密码输入不一致或密码长度不符合要求");
            alert.setResizable(false);
            // 设置对话框的内容文本
            alert.show(); // 显示对话框
        }else{
            Connection con=null;
            try{
                con=dbUtils.getCon();
                int n=studentDao.updatepassword(con,LoginController.account,this.b_password_text.getText());
                if(n==1){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
                    alert.setTitle("修改成功!");
                    alert.setHeaderText("密码修改成功!"); // 设置对话框的头部文本
                    alert.setResizable(false);
                    alert.show();
                    this.password=this.b_password_text.getText();
                    reset_action(event);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR); // 创建一个消息对话框
                    alert.setTitle("修改失败!");
                    alert.setHeaderText("很抱歉,密码修改失败!"); // 设置对话框的头部文本
                    alert.setResizable(false);
                    alert.show();
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @FXML
    void reset_action(ActionEvent event) {
        this.a_password_text.setText("");
        this.b_password_text.setText("");
        this.c_password_text.setText("");
    }

    @FXML
    Parent createNode() throws IOException {
        return FXMLLoader.load(getClass().getResource("update_password.fxml"));
    }
}
