package com.zheng.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义Servlet处理用户请求,这里使用代码注册
 * Created by zhenglian on 2016/9/21.
 */
//@WebServlet(urlPatterns = "/myServlet/*", description = "通过代码注册Servlet")
public class MyServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("开始处理已知定义请求......");
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        out.println("hello world");
        out.flush();
        out.close();
    }
}
