package pers.Yuanchuan.dbwork.dao;

import pers.Yuanchuan.dbwork.model.Student;
import pers.Yuanchuan.dbwork.model.Students;
import pers.Yuanchuan.dbwork.utils.MD5_Encryption;
import pers.Yuanchuan.dbwork.utils.String_isEmpty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDao {
    /*
     * 学生信息添加
     */
    public int add(Connection con, Student student)throws Exception{
        String sql="insert into student values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,student.getId());
        pstmt.setString(2,student.getName());
        pstmt.setString(3,student.getSex());
        pstmt.setString(4,String.valueOf(student.getYear()));
        pstmt.setString(5,student.getType());
        pstmt.setString(6,student.getIdcard());
        pstmt.setString(7,student.getKhyh());
        pstmt.setString(8,student.getYhkh());
        pstmt.setString(9,student.getBanji());
        pstmt.setString(10,student.getMajor());
        return pstmt.executeUpdate();
    }
    /*
    学生账户添加
     */
    public int adduser(Connection con, String id)throws Exception{
        String sql="insert into user values(?,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,null);
        pstmt.setString(2,id);
        pstmt.setString(3,"670b14728ad9902aecba32e22fa4f6bd");
        pstmt.setString(4,"学生");
        return pstmt.executeUpdate();
    }
    /*
    查询专业号是否存在对应的专业
     */

    public ResultSet major(Connection con, Student student)throws Exception{
        String sql="select * from major where Major_id = ?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,student.getMajor());
        return pstmt.executeQuery();
    }

    /*
     *学生查询
     */
    public ResultSet query(Connection con, String id) throws Exception {
        StringBuffer sql = new StringBuffer("select * from student");
        if (String_isEmpty.isNotEmpty(id)) {
            sql.append(" and Stu_id = " + id);
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString().replaceFirst("and", "where"));
        return pstmt.executeQuery();
    }

    /*
    学生查询基本信息
     */
    public ResultSet query_basic(Connection con,String id) throws Exception{
        StringBuffer sql = new StringBuffer("select * from students");
        if (String_isEmpty.isNotEmpty(id)) {
            sql.append(" and Stu_id = " + id);
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString().replaceFirst("and", "where"));
        return pstmt.executeQuery();
    }

    public ResultSet query_basic1(Connection con,String name,String card,String id) throws Exception{
        StringBuffer sql = new StringBuffer("select * from students");
        if (String_isEmpty.isNotEmpty(id)) {
            sql.append(" and Stu_id = \"" + id + "\" and Stu_name = \"" + name + "\" and Stu_idcard = \""+ card+"\"");
        }
        PreparedStatement pstmt = con.prepareStatement(sql.toString().replaceFirst("and", "where"));
        return pstmt.executeQuery();
    }
    /*
     *学生删除
     */
//    public int delete(Connection con, String id) throws Exception {
//        //更新获奖信息表
//        String sql0 = "delete from stu_scholar where Stu_id=?";
//        PreparedStatement pstmt0 = con.prepareStatement(sql0);
//        pstmt0.setString(1, id);
//        pstmt0.executeUpdate();
//
//        //更新学生表
//        String sql = "delete from student where Stu_id=?";
//        PreparedStatement pstmt = con.prepareStatement(sql);
//        pstmt.setString(1, id);
//        return pstmt.executeUpdate();
//    }
    /*
    学生对应的账户删除
     */
    public int deleteuser(Connection con, String id) throws Exception {
        //更新学生表
        String sql = "delete from user where User_name=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, id);
        return pstmt.executeUpdate();
    }
    /*
     *奖学金更新
     */
    public int update(Connection con, Student student)throws Exception{
        String sql="update student set Stu_name=?,Stu_sex=?,Stu_year=?,Stu_type=?,Stu_idcard=?,Stu_khyh=?,Stu_yhkh=?,Stu_class=?,Major_id=? where Stu_id=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,student.getName());
        pstmt.setString(2,student.getSex());
        pstmt.setString(3,String.valueOf(student.getYear()));
        pstmt.setString(4,student.getType());
        pstmt.setString(5,student.getIdcard());
        pstmt.setString(6,student.getKhyh());
        pstmt.setString(7,student.getYhkh());
        pstmt.setString(8,student.getBanji());
        pstmt.setString(9,student.getMajor());
        pstmt.setString(10,student.getId());
        return pstmt.executeUpdate();
    }
    /*
    用户密码修改
     */
    public int updatepassword(Connection con,String a,String b) throws Exception{
        String sql="update user set User_password= ? where User_name=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, MD5_Encryption.md5(b));
        pstmt.setString(2,a);
        return pstmt.executeUpdate();
    }
}
