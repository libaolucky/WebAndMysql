package com.hp.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "XianServlet",urlPatterns = "/XianServlet")
public class XianServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //使用hutool 工具  创建验证码
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        //CircleCaptcha captcha = new CircleCaptcha(200, 100, 4, 20);
        //2.拿到验证码
        String code=lineCaptcha.getCode();
        //3.获取session
        HttpSession session=req.getSession();
        //4.把验证码  放入到  session中
        session.setAttribute("code",code);
        //5.将验证码发送到浏览器
        ServletOutputStream outputStream=resp.getOutputStream();
        lineCaptcha.write(outputStream);
        outputStream.close();


//        //图形验证码写出，可以写出到文件，也可以写出到流
////         captcha.write("d:/circle.png");
////        //验证图形验证码的有效性，返回boolean值
////            captcha.verify("1234");
    }
}
