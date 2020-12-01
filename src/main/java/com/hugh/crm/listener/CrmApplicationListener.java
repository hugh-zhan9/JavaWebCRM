package com.hugh.crm.listener;

import com.hugh.crm.pojo.DicValue;
import com.hugh.crm.service.DicService;
import com.hugh.crm.service.impl.DicServiceImpl;
import com.hugh.crm.util.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.*;

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

        // 处理Stage2Possibility.properties文件中的键值对关系解析成Java数据
        Map<String,String> stage2Possibility = new HashMap<>();
        ResourceBundle bundle = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> enumKeys = bundle.getKeys();
        while (enumKeys.hasMoreElements()){
            String key = enumKeys.nextElement();
            String value = bundle.getString(key);
            stage2Possibility.put(key,value);
        }
        servletContext.setAttribute("stage2Possibility",stage2Possibility);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
