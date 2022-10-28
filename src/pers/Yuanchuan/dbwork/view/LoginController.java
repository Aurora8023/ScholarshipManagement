package pers.Yuanchuan.dbwork.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pers.Yuanchuan.dbwork.dao.StudentDao;
import pers.Yuanchuan.dbwork.dao.UserDao;
import pers.Yuanchuan.dbwork.model.Students;
import pers.Yuanchuan.dbwork.model.User;
import pers.Yuanchuan.dbwork.utils.Db_utils;
import pers.Yuanchuan.dbwork.utils.String_isEmpty;
import pers.Yuanchuan.dbwork.utils.MD5_Encryption;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class LoginController {

    public static String account;//全局变量，记录登陆账号
    public static String password;//全局变量，修改密码时比较原密码


    private Db_utils dbUtils=new Db_utils();
    @FXML
    private TextField userName_txt;

    @FXML
    private PasswordField password_txt;

    @FXML
    private Button login_btn;

    @FXML
    private Button reset_btn;

    @FXML
    private TextField name_txt;

    @FXML
    private TextField card_txt;

    @FXML
    private TextField id_txt;


    @FXML
    private AnchorPane tab_pane;

    @FXML
    private AnchorPane query_pane;

    @FXML
    private TextField yzm_txt;


    private StudentDao studentDao=new StudentDao();


    private UserDao userDao=new UserDao();

    /*
     *登录按钮事件处理
     */
    @FXML
    void login_event(ActionEvent event) {
        String userName = this.userName_txt.getText();
        String password = this.password_txt.getText();
        this.account = userName;//学号
        this.password=password;

        if(String_isEmpty.isEmpty(userName)){
            Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
            alert.setTitle("好像出了点问题...");
            alert.setHeaderText("用户名不能为空!"); // 设置对话框的头部文本
            alert.setContentText("请重新输入用户名和密码");
            alert.setResizable(false);
            // 设置对话框的内容文本
            alert.show(); // 显示对话框
        }else if(String_isEmpty.isEmpty(password)){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("好像出了点问题...");
            alert.setHeaderText("密码不能为空!"); // 设置对话框的头部文本
            alert.setContentText("请重新输入用户名和密码");
            alert.setResizable(false);
            // 设置对话框的内容文本
            alert.show(); // 显示对话框
        }
        else{
//            System.out.println(LoginStart.random);
            int pd=LoginStart.random;
            int su=0;
            if(pd==0){
                if(yzm_txt.getText().equals("0010")){
                    su=1;
                }
            }
            if(pd==1){
                if(yzm_txt.getText().equals("0495")){
                    su=1;
                }
            }
            if(pd==2){
                if(yzm_txt.getText().equals("1622")){
                    su=1;
                }
            }
            if(pd==3){
                if(yzm_txt.getText().equals("3783")){
                    su=1;
                }
            }
            if(pd==4){
                if(yzm_txt.getText().equals("5293")){
                    su=1;
                }
            }
            if(pd==5){
                if(yzm_txt.getText().equals("7679")){
                    su=1;
                }
            }
            if(pd==6){
                if(yzm_txt.getText().equals("8760")){
                    su=1;
                }
            }
            if(pd==7){
                if(yzm_txt.getText().equals("9091")){
                    su=1;
                }
            }
            if(pd==8){
                if(yzm_txt.getText().equals("9596")){
                    su=1;
                }
            }

            if(su==0){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("好像出了点问题...");
                alert.setHeaderText("验证码错误!"); // 设置对话框的头部文本
                alert.setContentText("请重新输入验证码");
                alert.setResizable(false);
                // 设置对话框的内容文本
                alert.show(); // 显示对话框
                yzm_txt.clear();

            }
            else{
                password = MD5_Encryption.md5(password); //对密码进行MD5加密
                User user=new User(userName,password);
                Connection con=null;
                try {
                    con=dbUtils.getCon();
                    User current_user=userDao.login(con,user);
                    if(current_user!=null){
                        if(current_user.getIdentity().equals("管理员")){
                            Platform.runLater(() -> {
                                //创建主界面窗口
                                try {
                                    new MainFrameStart().start(new Stage());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //关闭登陆窗口
                                Stage now=(Stage)login_btn.getScene().getWindow();
                                now.hide();
                            });
                        }else if(current_user.getIdentity().equals("学生")){
                            Platform.runLater(() -> {
                                //创建主界面窗口
                                try {
                                    new MainFrameStart_stu().start(new Stage());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //关闭登陆窗口
                                Stage now=(Stage)login_btn.getScene().getWindow();
                                now.hide();
                            });
                        }

                    }else {
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("好像出了点问题...");
                        alert.setHeaderText("用户名或密码错误!"); // 设置对话框的头部文本
                        alert.setContentText("请确认后重新输入用户名和密码");
                        alert.setResizable(false);
                        // 设置对话框的内容文本
                        alert.show(); // 显示对话框
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     *重置按钮事件处理
     */
    @FXML
    void reset_event(ActionEvent event) {
        query_pane.setVisible(false);
        tab_pane.toFront();
        tab_pane.setVisible(true);
    }

    @FXML
    void return_event(ActionEvent event){
        tab_pane.setVisible(false);
        query_pane.toFront();
        query_pane.setVisible(true);
    }

    @FXML
    void set_event(ActionEvent event) throws Exception {
        if(this.name_txt.getText().equals("")||this.card_txt.getText().equals("")||this.id_txt.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
            alert.setTitle("您输入的信息不完整!");
            alert.setHeaderText("请填写完整的信息"); // 设置对话框的头部文本
            alert.setResizable(false);
            alert.show();
        }else{
            Connection con=null;
            con=dbUtils.getCon();
            ResultSet rs=studentDao.query_basic1(con,this.name_txt.getText(),this.card_txt.getText(),this.id_txt.getText());
            if(rs.isBeforeFirst()){
                studentDao.updatepassword(con,this.id_txt.getText(),"000000");
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // 创建一个消息对话框
                alert.setTitle("重置成功!");
                alert.setHeaderText("密码重置成功!"); // 设置对话框的头部文本
                alert.setContentText("您的密码已经重置为初始密码：000000");
                alert.setResizable(false);
                alert.show();
                return_event(event);
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                alert.setTitle("您输入的信息有误!");
                alert.setHeaderText("请检查输入的信息"); // 设置对话框的头部文本
                alert.setContentText("若检查无误后仍无法重置，请联系管理员");
                alert.setResizable(false);
                alert.show();
            }
        }
    }

}
