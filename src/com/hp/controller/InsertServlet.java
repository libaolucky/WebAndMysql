package com.hp.controller;

import com.alibaba.fastjson.JSON;
import com.hp.bean.User;
import com.hp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "InsertServlet",urlPatterns = "/InsertServlet")
public class InsertServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.修正编码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        System.out.println("===========================");
        //2.接收前端传来的参数
       String username= req.getParameter("username");
        System.out.println("username = " + username);
       String real_name=req.getParameter("real_name");
       String password=req.getParameter("password");
       String type=req.getParameter("type");
        System.out.println("type = " + type);

       String is_del=req.getParameter("is_del");
        System.out.println("is_del = " + is_del);

       String img=req.getParameter("img");
       String create_time=req.getParameter("create_time");
       String modify_time=req.getParameter("modify_time");


        User user=new User();
        user.setUsername(  username  );
        user.setReal_name(  real_name  );
        user.setPassword(  password  );
        String typess=(type.equals("管理员")?"1":"2");
        user.setType(Integer.parseInt(typess));
        user.setIs_del(Integer.parseInt(is_del));
        user.setImg( img );
        user.setCreate_time( create_time );
        user.setModify_time(modify_time);

        System.out.println("user = " + user);

        UserService userService=new UserService();
        Map map = userService.insertByUser(user);
        System.out.println("map = " + map);

        String s= JSON.toJSONString(map);
        System.out.println("s = " + s);

        PrintWriter printWriter=resp.getWriter();
        printWriter.println(s);
        printWriter.close();
    }
}
