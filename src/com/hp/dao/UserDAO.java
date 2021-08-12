package com.hp.dao;

import com.hp.bean.User;
import com.hp.util.DBHelper;
import com.hp.util.PageBeanUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//dao 层应该是个接口，  为什么 因为可以 使用 aop  你不用aop,就可以直接写成类
public class UserDAO {
    //增删改查

    //登录  select * from t_user where username=? and password =?;
    // 登陆  select * from t_user where username =? and password =?
    public User login(String username,String password){
        User user =null;
        // 1. 创建链接
        Connection connection = DBHelper.getConnection();
        // 2. 建出 sql 语句
        String sql = " select * from t_user where username = ? and password = ?  ";
        // 3. 使用链接对象 获取 预编译对象
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            // 4. 执行 预编译对象,得出结果集
            rs = ps.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    


    //全查询
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
               User  user =new User();
                System.out.println("username"+rs.getString("username")); //拿到每一个 row
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

    //动态的带参数的分页查询   以后mybatis 会简化
    // page是我们的页数     limit 是条数
    public List<User> selectAllByParam(Map map){
        System.out.println("map = " + map);
        System.out.println(" jinqu dao" );

        String page=(String) map.get("page");   //接收前端的参数 放入到map中，这里直接获取就行了
        String limit =(String)map.get("limit");
        String real_name=(String)map.get("real_name");
        String type=(String)map.get("type");
        String username=(String)map.get("username");
        //如果说  real_name  不为空
        //  sql= select * from t_user where real_name like %张% limit ?,?
        //如果说 type 不为空   real_name 不为空
        //  sql= select * from t_user where real_name like %张%  and type=1 limit ?,?
        //如果说 type 不为空   real_name 不为空 username 不为空
        //  sql= select * from t_user where real_name like %张%  and type=1  and username like %李% limit ?,?


        List<User> lists=new ArrayList<>();
        //1：创建出连接对象
        Connection connection= DBHelper.getConnection();
        //2：创建出sql语句
//        String sql="select * from t_user where +"+
//                "           "+
//                ""+
//                "   +       limit ?,?  ";
        String sql="select * from t_user where 1=1";  //where 1=1 因为有多余的  and
        if(null!=real_name && real_name.length()>0){
            sql =sql +" and real_name like '%"+real_name+"%' ";
        }
        if(null!=type && type.length()>0){
            sql =sql +" and type = "+type+"   ";
        }
        if(null!=username && username.length()>0){
            sql =sql +" and username like '%"+username+"%' ";
        }
        sql=sql+" limit  ? , ?";
        System.out.println("da de  sql= " + sql);

        //3.预编译 sql
        PreparedStatement ps= null;
        ResultSet rs=null;
        PageBeanUtil pageBeanUtil=new PageBeanUtil(Integer.parseInt(page),Integer.parseInt(limit));  //因为第一个问号需要求出来
        try {
            ps=connection.prepareStatement(sql);
            ps.setInt(1,pageBeanUtil.getStart());  //这是索引
            ps.setInt(2,Integer.parseInt(limit));
            //4.执行 sql
            rs=ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                System.out.println("username" + rs.getString("username")); //拿到每一个 row
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
                lists.add(user);
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


        return lists;
    }
    
    //查询总条数
    public int selectCount(Map map1){

        String real_name=(String)map1.get("real_name");
        String type=(String)map1.get("type");
        String username=(String)map1.get("username");

        //1.开连接
       Connection connection= DBHelper.getConnection();
       //2.书写sql语句
        String sql="select count(*)  total from t_user where 1=1";
        if(null!=real_name && real_name.length()>0){
            sql =sql +" and real_name like '%"+real_name+"%' ";
        }
        if(null!=type && type.length()>0){
            sql =sql +" and type = "+type+"   ";
        }
        if(null!=username && username.length()>0){
            sql =sql +" and username like '%"+username+"%' ";
        }
        System.out.println("sql count的 = " + sql);

        //3.预编译
        PreparedStatement ps=null;
        ResultSet rs=null;
        int total=0;
        try {
            ps=connection.prepareStatement(sql);
            //4.执行
          rs= ps.executeQuery();
          if(rs.next()){
              total= rs.getInt("total");
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


        return total;
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

    //更新 is_del
    public int updateisdel(User user){
        //步骤1：创建出连接对象
        Connection connection= DBHelper.getConnection();
        //步骤2：创建出sql语句  因为 添加的数据时变量，所以要用  ?代替
        String sql="update t_user set is_del=? where id=?";
        PreparedStatement ps=null;
        int a=0;
        try {
            //3.预编译  sql
            ps=connection.prepareStatement(sql);
            ps.setInt(1,user.getIs_del());
            ps.setInt(2,user.getId());

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
//        user.setId(62);
//        int i=dao.del(user);
//        System.out.println("i = " + i);
        //登录测试
//        User abc = dao.login("abc", "123456");
//        System.out.println("abc = " + abc);

        //分页查询的测试
//        List<User> users=dao.selectAllByParam(1,5);
//        System.out.println("users = " + users);
//        System.out.println("users 长度=" + users.size());
        
        //查总条数
//        int i=dao.selectCount();
//        System.out.println("i = " + i);

        user.setIs_del(1);
        user.setId(8);
        int up2=dao.updateisdel(user);
        System.out.println("up2 = " + up2);
        
    }
}
