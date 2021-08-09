package com.hp.dao;
import com.hp.bean.User;
import com.hp.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//dao 层应该是个接口，  为什么 因为可以 使用 aop  你不用aop,就可以直接写成类
public class UserDAO {
    //增删改查
    //查询
    public List<User> selectAll(){
        List<User> userlist=new ArrayList<>();

        //dao层如何和数据库做连接，我们用知识点叫做jdbc,做基础的一个必须的技术，
        // 很多框架 都是 基于这个jdbc来的，所有必须学
        //要连接数据库，就需要用到刚刚  DBHelper.getConnection(); 来创建一个 和mysql一样的连接对象
        //这个对象可以负责和mysql连接

        //步骤1：创建出连接对象
        Connection connection= DBHelper.getConnection();
        //步骤2：创建出sql语句
        String sql="select * from t_user";

        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            //步骤3：使用连接对象  获取预编译对象
           ps=connection.prepareStatement(sql);
            System.out.println("ps = " + ps);
            //步骤4：执行预编译对象，得出结果集
           rs=ps.executeQuery();
            //步骤5：遍历结果集   一一的获取对象
            while (rs.next()){
                System.out.println("username"+rs.getString("username")); //拿到每一个 row
                User user=new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
                userlist.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userlist;
    }

    //新增
    public int addUser(User user){
        //步骤1：创建出连接对象
        Connection connection= DBHelper.getConnection();
        //步骤2：创建出sql语句  因为 添加的数据时变量，所以要用  ?代替
        String sql="insert into t_user  values (null,?,?,?,?,?,?,?,?)";

        PreparedStatement ps=null;
        int i=0;
        try {
            //3.预编译  sql
            ps=connection.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getReal_name());
            ps.setString(4,user.getImg());
            ps.setInt(5,user.getType());
            ps.setInt(6,user.getIs_del());
            ps.setString(7,user.getCreate_time());
            ps.setString(8,user.getModify_time());
            
            //4.执行预编译对象
            i= ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  i;
    }

    //修改
    public int update(User user){
        //步骤1：创建出连接对象
        Connection connection= DBHelper.getConnection();
        //步骤2：创建出sql语句  因为 添加的数据时变量，所以要用  ?代替
        String sql="update t_user set username=?,password=?,real_name=?,img=?,type=?,is_del=?,create_time=?,modify_time=? where id=?";
        PreparedStatement ps=null;
        int a=0;
        try {
            //3.预编译  sql
            ps=connection.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getReal_name());
            ps.setString(4,user.getImg());
            ps.setInt(5,user.getType());
            ps.setInt(6,user.getIs_del());
            ps.setString(7,user.getCreate_time());
            ps.setString(8,user.getModify_time());
            ps.setInt(9,user.getId());

            //4.执行预编译对象
            a= ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  a;
    }
    
    //删除
    public int del(User user){
        //1.创建连接对象
        Connection connection=DBHelper.getConnection();
        //2.书写sql语句
        String sql="delete from t_user where id=?";
       
        PreparedStatement ps=null;
        int b=0;
        try {
            //3. 预编译 sql 语句
            ps=connection.prepareStatement(sql);
            ps.setInt(1,user.getId());
            //4.执行  预编译对象
            b=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return b;
    }


    public static void main(String[] args) {
        UserDAO dao=new UserDAO();
        //全查的实现
       // List<User> users=dao.selectAll();
        //for (User user : users) {
        //    System.out.println("user = " + user);
        //}
        //添加的实现
         User user=new User();
//        user.setUsername("heihei");
//        user.setType(1);
//        user.setReal_name("嘿嘿");
//        user.setPassword("1234");
//        user.setModify_time("2012-09-12");
//        user.setIs_del(1);
//        user.setImg("xxx");
//        user.setCreate_time("2013-09-12");
//        int i=dao.addUser(user);
//        System.out.println("i = " + i);

        //修改的实现
//        user.setUsername("baitu");
//        user.setType(3);
//        user.setReal_name("白兔");
//        user.setPassword("12348");
//        user.setModify_time("2009-12-02");
//        user.setIs_del(5);
//        user.setImg("xxx");
//        user.setCreate_time("2021-09-08");
//        user.setId(24);
//        int i=dao.update(user);
//        System.out.println("i = " + i);
        
        //删除的实现
        user.setId(62);
        int i=dao.del(user);
        System.out.println("i = " + i);
    }
}
