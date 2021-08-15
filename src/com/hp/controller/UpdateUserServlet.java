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

@WebServlet(name = "UpdateUserServlet",urlPatterns = "/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.修正编码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        System.out.println("===========================");
        //2.接收前端传来的参数
        String id=req.getParameter("id");
        String username= req.getParameter("username");
        System.out.println("username = " + username);
        String real_name=req.getParameter("real_name");
        String password=req.getParameter("password");
        String type=req.getParameter("type");
        System.out.println("type = " + type);

        String is_del=req.getParameter("is_del");
        System.out.println("is_del = " + is_del);

        String img=req.getParameter("img");
        String modify_time=req.getParameter("modify_time");

        //给User 对象  重新赋值
        User user1=new User();
        user1.setId(Integer.parseInt(id));
        user1.setUsername(username);
        user1.setReal_name(real_name);
        user1.setPassword(password);
        String typess=(type.equals("管理员")?"1":"2");
        user1.setType(Integer.parseInt(typess));
        user1.setIs_del(Integer.parseInt(is_del));
        user1.setImg(img);
        user1.setModify_time(modify_time);

        System.out.println("user1 = " + user1);


        UserService userService=new UserService();
        Map map = userService.updateUser(user1);
        System.out.println("map = " + map);

        String s= JSON.toJSONString(map);
        System.out.println("s = " + s);

        PrintWriter printWriter=resp.getWriter();
        printWriter.println(s);
        printWriter.close();
    }
}
