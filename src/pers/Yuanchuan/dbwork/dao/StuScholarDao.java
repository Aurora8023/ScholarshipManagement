package pers.Yuanchuan.dbwork.dao;

import pers.Yuanchuan.dbwork.model.Student;
import pers.Yuanchuan.dbwork.utils.String_isEmpty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StuScholarDao {
    /*
     *查询学生获奖信息
     */
    public ResultSet query(Connection con, Student student) throws Exception{
        StringBuffer sql=
                new StringBuffer("SELECT student.Stu_id,student.Stu_name,major.Major_name,scholarship.Scholar_name,stu_scholar.exam,stu_scholar.state FROM stu_scholar LEFT JOIN student ON student.Stu_id=stu_scholar.Stu_id LEFT JOIN scholarship ON scholarship.Scholar_id=stu_scholar.Scholar" +
                        "_id LEFT JOIN major ON major.Major_id=student.Major_id");
        if(String_isEmpty.isNotEmpty(student.getId())){
            sql.append(" and student.Stu_id = '" +student.getId()+"'");
        }
        if(String_isEmpty.isNotEmpty(student.getName())){
            sql.append(" and student.Stu_name = '"+student.getName()+"'");
        }
        PreparedStatement pstmt=con.prepareStatement(sql.toString().replaceFirst("and","where"));
        return pstmt.executeQuery();
    }

    public ResultSet query_copy(Connection con, Student student) throws Exception{
        StringBuffer sql=
                new StringBuffer("SELECT student.Stu_id,student.Stu_name,major.Major_name,scholarship.Scholar_name,stu_scholar.exam,stu_scholar.state FROM stu_scholar LEFT JOIN student ON student.Stu_id=stu_scholar.Stu_id LEFT JOIN scholarship ON scholarship.Scholar_id=stu_scholar.Scholar" +
                        "_id LEFT JOIN major ON major.Major_id=student.Major_id");
        if(String_isEmpty.isNotEmpty(student.getId())){
            sql.append(" and student.Stu_id like '%"+student.getId() + "%'");
        }
        if(String_isEmpty.isNotEmpty(student.getName())){
            sql.append(" and student.Stu_name like '%"+student.getName()+"%'");
        }
        PreparedStatement pstmt=con.prepareStatement(sql.toString().replaceFirst("and","where"));
        return pstmt.executeQuery();
    }

    /*
    查询某个奖学金对应的学生获奖信息
     */

    public ResultSet query_grant(Connection con, String sid)throws Exception{
//        StringBuffer sql = new StringBuffer("SELECT * FROM scholar_grant");
//        sql.append(" where sid = "+sid+" and exam = ");
//        PreparedStatement pstmt=con.prepareStatement(sql.toString());

        String sql="SELECT * FROM scholar_grant where sid = ? and exam = ? and state = ?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,sid);
        pstmt.setString(2,"审核通过");
        pstmt.setString(3,"未发放");
        return pstmt.executeQuery();
    }

    public ResultSet query_xs(Connection con)throws Exception{
//        StringBuffer sql = new StringBuffer("SELECT * FROM scholar_grant");
//        sql.append(" where sid = "+sid+" and exam = ");
//        PreparedStatement pstmt=con.prepareStatement(sql.toString());

        String sql="call pr_xs()";
        PreparedStatement pstmt=con.prepareStatement(sql);
        return pstmt.executeQuery();
    }

    public ResultSet query_tj(Connection con)throws Exception{
//        StringBuffer sql = new StringBuffer("SELECT * FROM scholar_grant");
//        sql.append(" where sid = "+sid+" and exam = ");
//        PreparedStatement pstmt=con.prepareStatement(sql.toString());

        String sql="call pr_tj()";
        PreparedStatement pstmt=con.prepareStatement(sql);
        return pstmt.executeQuery();
    }

    public ResultSet query_grant1(Connection con, String sid)throws Exception{
        String sql="SELECT * FROM scholar_grant where sid = ? and exam = ?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,sid);
        pstmt.setString(2,"待审核");
        return pstmt.executeQuery();
    }

    public ResultSet query_grant2(Connection con, String sid)throws Exception{
        String sql="SELECT * FROM scholar_grant where sid = ? and state = ?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,sid);
        pstmt.setString(2,"未发放");
        return pstmt.executeQuery();
    }

    public ResultSet query_grant3(Connection con, String sid)throws Exception{
        String sql="SELECT * FROM scholarship where Scholar_id = ?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,sid);
        return pstmt.executeQuery();
    }



    public int set_grant(Connection con, String sid)throws Exception{
//        StringBuffer sql = new StringBuffer("SELECT * FROM scholar_grant");
//        sql.append(" where sid = "+sid+" and exam = ");
//        PreparedStatement pstmt=con.prepareStatement(sql.toString());

        String sql="update scholar_grant set state = ? where sid = ? and exam = ?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,"已发放");
        pstmt.setString(2,sid);
        pstmt.setString(3,"审核通过");
        return pstmt.executeUpdate();
    }

    /*
     *添加学生获奖信息
     */
    public int add(Connection con, String Stu_id, int Scholar_id,String exam,String state) throws Exception{
        String sql="insert into stu_scholar values(?,?,?,?,?)";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,null);
        pstmt.setString(2,Stu_id);
        pstmt.setString(3,String.valueOf(Scholar_id));
        pstmt.setString(4,exam);
        pstmt.setString(5,state);
        return pstmt.executeUpdate();
    }

    /*
     *查询获奖表信息(上面那个查询是详细查出每一个,这个查询是仅在获奖表中查)
     */
    public ResultSet query_table(Connection con, String stu_id, String scholar_id,String state)throws Exception{
        StringBuffer sql = new StringBuffer("SELECT Stu_id,Scholar_id,state FROM stu_scholar");
        if(String_isEmpty.isNotEmpty(stu_id)){
            sql.append(" and Stu_id like '%"+stu_id+"%'");
        }
        if(String_isEmpty.isNotEmpty(scholar_id)){
            sql.append(" and Scholar_id like '%"+scholar_id+"%'");
        }
        PreparedStatement pstmt=con.prepareStatement(sql.toString().replaceFirst("and","where"));
        return pstmt.executeQuery();
    }

    public ResultSet query_table1(Connection con, String stu_id, String scholar_id,String exam,String state)throws Exception{
        StringBuffer sql = new StringBuffer("SELECT Stu_id,Scholar_id,exam,state FROM stu_scholar");
        if(String_isEmpty.isNotEmpty(stu_id)){
            sql.append(" and Stu_id = "+stu_id);
        }
        if(String_isEmpty.isNotEmpty(scholar_id)){
            sql.append(" and Scholar_id = "+scholar_id);
        }
        PreparedStatement pstmt=con.prepareStatement(sql.toString().replaceFirst("and","where"));
        return pstmt.executeQuery();
    }

    /*
     *修改获奖信息表
     */
    public int update(Connection con,String stu_id,int scholar_id,String exam,String state)throws Exception{
        String sql="update stu_scholar set exam=?,state=? where Stu_id=? and Scholar_id=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,exam);
        pstmt.setString(2,state);
        pstmt.setString(3,stu_id);
        pstmt.setString(4,String.valueOf(scholar_id));
        System.out.println(pstmt.executeUpdate());
        return pstmt.executeUpdate();
    }

    /*
     *删除部分的查询
     */
    public ResultSet query_del(Connection con,String stu_id,String scholar_id) throws Exception{
        StringBuffer sql=
                new StringBuffer("SELECT student.Stu_id,student.Stu_name,major.Major_name,scholarship.Scholar_name,stu_scholar.exam,stu_scholar.state FROM stu_scholar LEFT JOIN student ON student.Stu_id=stu_scholar.Stu_id LEFT JOIN scholarship ON scholarship.Scholar_id=stu_scholar.Scholar_id LEFT JOIN major ON major.Major_id=student.Major_id");
        if(String_isEmpty.isNotEmpty(stu_id)){
            sql.append(" and student.Stu_id = \""+stu_id+"\"");
        }
        if(String_isEmpty.isNotEmpty(scholar_id)){
            sql.append(" and scholarship.Scholar_id = \""+scholar_id+"\"");
        }
        PreparedStatement pstmt=con.prepareStatement(sql.toString().replaceFirst("and","where"));
        return pstmt.executeQuery();
    }

    /*
     *学生获奖信息删除
     */
    public int delete(Connection con, String stu_id, String scholar_id) throws Exception {
        //更新获奖信息表
        String sql = "delete from stu_scholar where Stu_id=? and Scholar_id=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, stu_id);
        pstmt.setString(2,scholar_id);
        System.out.println(pstmt.executeUpdate());
        return pstmt.executeUpdate();
    }
}
