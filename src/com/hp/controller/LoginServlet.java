package com.hp.controller;

import com.alibaba.fastjson.JSONObject;
import com.hp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

//接收login.html传来的参数
@WebServlet(name = "LoginServlet",urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. 修正 编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8"); // 注意 , 别写错

        //2. 接收 前端过来的 3个参数
        String userName = req.getParameter("userName");
        String userPwd = req.getParameter("userPwd");
        String code = req.getParameter("code");
        System.out.println("code = " + code);
        //3. 登陆的时候,首先要验证 验证码是否正确
        // 3.1 获取 后台的验证码!
        HttpSession session = req.getSession();
        String codeFromSession = (String) session.getAttribute("code");
        System.out.println("codeFromSession = " + codeFromSession);
        if (!codeFromSession.equals(code)){
            //验证错误, 注意  前面有 !
            // 向前断 输入 一段json ,告知前端  验证码错误了.
            PrintWriter writer = resp.getWriter();
            Map map =new HashMap();
            map.put("code",400);
            map.put("msg","验证码不正确");
            // 把map 变为 json
            String jsonString = JSONObject.toJSONString(map);
            writer.print(jsonString);
            writer.close();
        }else{
            // 验证码正确 , 继续 判断 账号和密码
            System.out.println(" 验证码正确, 该判断 账号和 密码了 ");
            // 就需要 service / dao 层判断. 如果 咱们业务不是特别的多, 那么
            // 可以直接 不用 service 层
            UserService service = new UserService();
            Map map = service.login(userName, userPwd, req);
            String jsonString = JSONObject.toJSONString(map);
            PrintWriter writer = resp.getWriter();
            writer.print(jsonString);
            writer.close();
        }
    }
}
