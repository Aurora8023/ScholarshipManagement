package pers.Yuanchuan.dbwork.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import pers.Yuanchuan.dbwork.dao.StudentDao;
import pers.Yuanchuan.dbwork.model.Students;
import pers.Yuanchuan.dbwork.utils.Db_utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Random;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JFrame;

import static com.sun.glass.ui.Cursor.setVisible;

public class QueryStuBasicController {

    @FXML
    private TextField stu_id_text;
    @FXML
    private TextField stu_name_text;
    @FXML
    private TextField stu_sex_text;
    @FXML
    private TextField stu_year_text;
    @FXML
    private TextField stu_type_text;
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
    private TextField stu_faculty_text;
    @FXML
    private TextField stu_fdy_text;

    @FXML
    private TextField password_text;

    @FXML
    private AnchorPane tab_pane;

    @FXML
    private AnchorPane query_pane;




    private static Random random = new Random();
    private int width = 53;//验证码宽度
    private int height =25;//验证码高度
    private int font_size = 20;//验证码颜色
    private int x = 100;//验证码所在窗体X坐标
    private int y = 100;//验证码所在窗体Y坐标
    private int jam = 5;//干扰元素 建议使用 4~7 之间的数字
    private String code = "";//保存验证码



    private Db_utils dbUtils=new Db_utils();
    private Students stu;
    private StudentDao studentDao=new StudentDao();
    @FXML
    void query_action(ActionEvent event) {
        if(!this.password_text.getText().equals(UpdatePasswordController.password)) {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("身份验证失败");
            alert.setHeaderText("密码有误!"); // 设置对话框的头部文本
            alert.setContentText("请检查后重新输入!");
            alert.setResizable(false);
            // 设置对话框的内容文本
            alert.show(); // 显示对话框
        }else{
            String id=LoginController.account;//id为登陆时用户名
            Connection con=null;
            try {
                con=dbUtils.getCon();
                ResultSet rs=studentDao.query_basic(con,id);
                if(rs.isBeforeFirst()){
                    while(rs.next()){
                        stu=new Students(rs.getString("Stu_id"),rs.getString("Stu_name"),
                                rs.getString("Stu_sex"),rs.getInt("Stu_year"),
                                rs.getString("Stu_type"),rs.getString("Stu_idcard"),
                                rs.getString("Stu_khyh"),rs.getString("Stu_yhkh"),
                                rs.getString("Stu_class"),rs.getString("Stu_major"),
                                rs.getString("Stu_faculty"),rs.getString("Stu_fdy"));
                        fill_text(stu);//将从数据库返回的学生信息填到表中
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING); // 创建一个消息对话框
                    alert.setTitle("该学生信息不存在!");
                    alert.setHeaderText("不存该学号对应的学生"); // 设置对话框的头部文本
                    alert.setContentText("请确定学生学号是否正确");
                    alert.setResizable(false);
                    alert.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void fill_text(Students stu) {
        this.stu_id_text.setText(stu.getId());
        this.stu_name_text.setText(stu.getName());

//        if(stu.getSex().equals("男")){
//            this.man.setSelected(true);
//        }else {
//            this.woman.setSelected(true);
//        }
        this.stu_sex_text.setText(stu.getSex());


        this.stu_year_text.setText(String.valueOf(stu.getYear()));
        this.stu_type_text.setText(stu.getType());
        this.stu_idcard_text.setText(stu.getIdcard());
        this.stu_khyh_text.setText(stu.getKhyh());
        this.stu_yhkh_text.setText(stu.getYhkh());
        this.stu_class_text.setText(stu.getBanji());
        this.stu_major_text.setText(stu.getMajor());
        this.stu_faculty_text.setText(stu.getFaculty());
        this.stu_fdy_text.setText(stu.getFdy());
        show_tab();
    }

    private void show_tab() {
        query_pane.setVisible(false);
        tab_pane.toFront();
        tab_pane.setVisible(true);
    }
    
    @FXML
    Parent createNode() throws IOException {
        return FXMLLoader.load(getClass().getResource("query_stu1_basic.fxml"));
    }

    @FXML
    void reset_action(ActionEvent event) {
        this.password_text.setText("");
    }


    public Color getRandomColor(){//获得随机颜色
        int R=random.nextInt(255),G=random.nextInt(255),B=random.nextInt(255);
        return new Color(R,G,B);
    }

    public String getRandomString(){//获得验证码
        int num = random.nextInt(9);
        code = num+"";
        return num+"";
    }

    public void checkCode(Graphics g){// 绘画验证码
        drawBorder(g);
        drawCode(g);
        drawJam(g);
    }

    public void drawBorder(Graphics g){//绘画边框和背景
        Color gc = g.getColor();
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        g.setColor(gc);
    }

    public void drawCode(Graphics g){//绘画验证码内容
        Color gc = g.getColor();
        for(int i=0;i<4;i++){
            g.setColor(getRandomColor());
            g.setFont(new Font("宋体",Font.BOLD,font_size));
            g.drawString(getRandomString(), x+5+(i*12), y+font_size);
        }
        g.setColor(gc);
    }

    public void drawJam(Graphics g){//绘画干扰元素
        Color gc = g.getColor();
        for(int i=0;i<jam;i++){
            g.setColor(getRandomColor());
            g.drawLine(x+random.nextInt(width), y+random.nextInt(height), x+random.nextInt(width), y+random.nextInt(height));
        }
        g.setColor(gc);
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.drawString("单击可刷新验证码", 30, 50);
        checkCode(g);
        g.setColor(c);
    }

}
