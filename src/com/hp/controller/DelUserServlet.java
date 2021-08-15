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

@WebServlet(name = "DelUserServlet",urlPatterns = "/DelUserServlet")
public class DelUserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.修正编码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        System.out.println("===========================");
        //2.接收前端传来的参数
        String userId=req.getParameter("userId");
        System.out.println("userId = " + userId);

        User user2=new User();
        user2.setId(Integer.parseInt(userId));

        UserService userService=new UserService();
        Map map = userService.delUser(user2);
        System.out.println("map = " + map);

        String s= JSON.toJSONString(map);
        System.out.println("s = " + s);

        PrintWriter printWriter=resp.getWriter();
        printWriter.println(s);
        printWriter.close();
    }
}
