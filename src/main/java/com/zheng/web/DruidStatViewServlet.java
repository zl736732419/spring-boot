package com.zheng.web;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * druid提供的监控监控
 * Created by zhenglian on 2016/9/20.
 */

@WebServlet(urlPatterns = "/druid/*",
    initParams = {
            @WebInitParam(name="allow", value="127.0.0.1"), //IP白名单
            @WebInitParam(name="deny", value="192.168.1.116"), //IP黑名单
            @WebInitParam(name="loginUsername", value="admin"), //设置登录账号
            @WebInitParam(name="loginPassword", value="123456"), //设置登录用户密码
            @WebInitParam(name="resetEnable", value="false") //禁用html页面上的reset all 功能
    }
)
public class DruidStatViewServlet extends StatViewServlet {
}
