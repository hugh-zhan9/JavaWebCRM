package com.hugh.crm.listener;

import com.hugh.crm.pojo.DicValue;
import com.hugh.crm.service.DicService;
import com.hugh.crm.service.impl.DicServiceImpl;
import com.hugh.crm.util.ServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.xml.ws.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebListener
public class CrmApplicationListener implements ServletContextListener {
    /*
    监听器不是Spring创建的对象，不能使用自动注入

    @Autowired
    DicService dicService;
    */

    DicService dicService = (DicService) ServiceFactory.getService(new DicServiceImpl());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        // 读取字典数据
        Map<String,List<DicValue>> dicValueMap = dicService.getDicValue();
        Set<String> keySet = dicValueMap.keySet();
        for (String key : keySet) {
            servletContext.setAttribute(key,dicValueMap.get(key));
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
