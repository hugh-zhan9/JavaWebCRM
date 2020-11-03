package com.hugh.crm.controller;

import com.hugh.crm.pojo.Activity;
import com.hugh.crm.pojo.ListResult;
import com.hugh.crm.pojo.PageListSearch;
import com.hugh.crm.pojo.Users;
import com.hugh.crm.service.ActivityService;
import com.hugh.crm.service.UserService;
import com.hugh.crm.service.impl.UserServiceImpl;
import com.hugh.crm.util.DateTimeUtil;
import com.hugh.crm.util.PrintJson;
import com.hugh.crm.util.UUIDUtil;
import javafx.scene.chart.ValueAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ActivityController {
    @Autowired
    ActivityService activityService;

    @RequestMapping(value = "/activity/activities.do", method = RequestMethod.GET)
    public void queryActivity(HttpServletResponse response){
        UserService userService = new UserServiceImpl();
        List<Users> users = userService.selectAllUsers();
        PrintJson.printJsonObj(response,users);
    }

    @RequestMapping(value = "/activity/activities.do", method = RequestMethod.POST)
    public void saveActivity(@RequestBody Activity activity,HttpServletRequest request, HttpServletResponse response){
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        Users user = (Users) request.getSession().getAttribute("user");
        activity.setCreateBy(user.getName());
        activity.setEditTime(DateTimeUtil.getSysTime());
        activity.setEditBy(user.getName());
        Integer num = activityService.saveActivity(activity);
        if(num==1){
            PrintJson.printJsonFlag(response,true);
        }else {
            PrintJson.printJsonFlag(response,false);
        }
    }

    @RequestMapping(value = "activity/activitiesRemark.do", method = RequestMethod.POST)
    public void queryActivityRemark(@RequestBody PageListSearch pageListSearch, HttpServletResponse response){

        // 条件查询和分页查询

        // 用于计算略过的记录数
        String pageNo =  pageListSearch.getPageNo();
        // 每页展示的记录数
        String pageSize = pageListSearch.getPageSize();


        Integer pageNoNum = Integer.valueOf(pageNo);
        Integer pageSizeNum = Integer.valueOf(pageSize);
        // 不知道为什么直接传String类型的参数分页查询会报错
        int skipPageNum = (pageNoNum-1)*pageSizeNum;
        // String skipPage = String.valueOf(skipPageNum);

        // 组装对象或创建Map传递
        Map<String,Object> activityMap = new HashMap<String, Object>();
        activityMap.put("skipPage",skipPageNum);
        activityMap.put("pageSize",pageSizeNum);
        // 下面这种写法也会导致SQL报错
        //activityMap.put("pageSize",pageListSearch.getPageSize());
        activityMap.put("name",pageListSearch.getName());
        activityMap.put("owner",pageListSearch.getOwner());
        activityMap.put("startDate",pageListSearch.getStartDate());
        activityMap.put("endDate",pageListSearch.getEndDate());



        ListResult<Activity> activityListResult = activityService.pageList(activityMap);
        PrintJson.printJsonObj(response,activityListResult);
    }

    @RequestMapping(value = "activity/delete.do",method = RequestMethod.POST)
    public void deleteActivity(HttpServletRequest request,HttpServletResponse response){
        String ids[] = request.getParameterValues("id");
        for (int count=0; count<ids.length; count++){
            Boolean flag = activityService.deleteActivity(ids[count]);
            PrintJson.printJsonFlag(response,flag);
        }

    }

    @RequestMapping(value = "activity/activity.do")
    public void queryActivityById(HttpServletRequest request,HttpServletResponse response){
        String id = request.getParameter("id");
        try {
            Activity activity = activityService.getActivityById(id);
            System.out.println(activity);
            PrintJson.printJsonObj(response,activity);
        }catch (Exception e){
            e.printStackTrace();
            PrintJson.printJsonObj(response,e);
        }

    }

    @RequestMapping(value = "activity/update.do")
    public void updateActivity(@RequestBody Activity activity,HttpServletResponse response){
        activity.setEditTime(DateTimeUtil.getSysTime());
        if (activityService.updateActivity(activity)==1) {
            PrintJson.printJsonFlag(response, true);
        }else{
            PrintJson.printJsonFlag(response,false);
        }
    }

    @RequestMapping(value = "activity/detail.do")
    public void queryActivityDetail(@RequestParam String id,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        Activity activity = activityService.getById(id);
        request.setAttribute("activity",activity);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
        //return "forward:workbench/activity/detail";
    }
}
