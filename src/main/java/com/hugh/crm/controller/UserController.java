package com.hugh.crm.controller;

import com.hugh.crm.pojo.Users;
import com.hugh.crm.service.UserService;
import com.hugh.crm.service.impl.UserServiceImpl;
import com.hugh.crm.util.MD5Util;
import com.hugh.crm.util.PrintJson;
import com.hugh.crm.util.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/user/login.do".equals(path)) {
            login(request,response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response){
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");

        // 将密码的明文形式转化为密文形式
        loginPwd = MD5Util.getMD5(loginPwd);

        // 接收浏览器端 IP地址
        String ip = request.getRemoteAddr();

        // 业务层开发使用代理类形态的接口对象(代理实现类对象)
        //UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        UserService us = new UserServiceImpl();

        // 使用try-catch，一旦登录失败抛出自定义异常
        try{
            Users user = us.login(loginAct, loginPwd, ip);

            request.getSession().setAttribute("user",user);

            // 如果登录成功，返回{"success":true}
            PrintJson.printJsonFlag(response,true);

        }catch (Exception e){
            e.printStackTrace();

            // 如果登陆失败，返回{"success":false,"msg",错误的原因}
            String msg = e.getMessage();

            System.out.println(msg);

            // 1. 传递map  2.传递对象 (如果对于展示的信息多处需要重复使用就创建对象)
            Map<String,Object> msgMap = new HashMap<String,Object>();
            msgMap.put("success",false);
            msgMap.put("msg",msg);
            PrintJson.printJsonObj(response,msgMap);

            System.out.println(msgMap);
        }

    }
}
