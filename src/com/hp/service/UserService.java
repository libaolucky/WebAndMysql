package com.hp.service;

import com.hp.bean.User;
import com.hp.dao.UserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class UserService {
    // 登陆
    public Map login(String username, String password, HttpServletRequest request){
        Map map = new HashMap();
        // service 层要调用dao层
        UserDAO dao = new UserDAO();
        User userFromDB = dao.login(username, password);
        if (  null == userFromDB  ) {
            // 没查不出, 即:账户名或者密码不正确
            map.put("code",4001);
            map.put("msg","账户名或者密码不正确");
            return map;
        }else{
            // 当登陆成功后,需要把 登陆的用户的信息放入到  session中去
            HttpSession session = request.getSession();
            session.setAttribute("user",userFromDB);
            map.put("code",0);
            map.put("msg","登陆成功");
            return map;
        }

    }

    //带参数的分页查询
    public Map selectAllByParam(int page,int limit){
        UserDAO userDAO=new UserDAO();
       List<User> users= userDAO.selectAllByParam(page,limit);
       int i=userDAO.selectCount();
       Map map=new HashMap();
       // map.put("code",0);
         map.put("code111",200);  //返回的数据不符合规范，正确的状态码是  code,0
        map.put("msg111","查询成功");
        map.put("count111",i);  //把死的写活了
        map.put("data111",users);

       //根据layui的返回的 json格式 去返回给你的数据，如果不一样，需要layui解析
        // {
        //  code:0,
        //  msg:" "
        // count:1000
        //data:[每条数据]
        // }
        Map map2=new HashMap();
        map2.put("number",20001);
        map2.put("message","数据查询成功");
        map2.put("object",map);


        return map2;

    }
}


