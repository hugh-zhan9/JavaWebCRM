package com.hugh.crm.controller;

import com.hugh.crm.pojo.Activity;
import com.hugh.crm.pojo.Clue;
import com.hugh.crm.pojo.ClueActivityRelation;
import com.hugh.crm.pojo.Users;
import com.hugh.crm.service.ClueActivityRelationService;
import com.hugh.crm.service.ActivityService;
import com.hugh.crm.service.ClueService;
import com.hugh.crm.service.UserService;
import com.hugh.crm.util.DateTimeUtil;
import com.hugh.crm.util.PrintJson;
import com.hugh.crm.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ClueController {

    @Autowired
    ClueService clueService;
    @Autowired
    UserService userService;
    @Autowired
    ClueActivityRelationService clueActivityRelationService;
    @Autowired
    ActivityService activityService;

    @PostMapping("clue/addClue.do")
    public void createClue(@RequestBody Clue clue, HttpServletRequest request, HttpServletResponse response){
        System.out.println(clue.getOwner());
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        Boolean flag = clueService.createClue(clue);
        PrintJson.printJsonFlag(response,flag);
    }

    @GetMapping("clue/userList.do")
    public void getUserList(HttpServletResponse response){
        List<Users> userList = userService.selectAllUsers();
        PrintJson.printJsonObj(response,userList);
    }

    @GetMapping("clue/detail.do")
    public void getDetail(String id,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        Clue clue = clueService.getDetail(id);
        request.setAttribute("clue",clue);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);
    }

    @GetMapping("clue/getActivityByClueId.do")
    public void getActivityById(HttpServletRequest request,HttpServletResponse response){
        String clueId = request.getParameter("clueId");
        List<Activity> activityList = clueActivityRelationService.getActivityByClueId(clueId);

        /*
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("activityList", activityList);
        map.get("activityList");
        */

        PrintJson.printJsonObj(response,activityList);
    }


    @PostMapping("clue/clueAndDeleteActivityRelation.do")
    public void deleteRelationById(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        Boolean flag = clueActivityRelationService.deleteClueActivityById(id);
        PrintJson.printJsonFlag(response,flag);
    }

    @GetMapping("clue/getAllClue.do")
    public void getAllClue(HttpServletRequest request, HttpServletResponse response){
        List<Clue> clues = clueService.getAllClue();
        PrintJson.printJsonObj(response,clues);
    }

    @GetMapping("clue/getAllActivity.do")
    public void getAllActivity(HttpServletRequest request, HttpServletResponse response){
        List<Activity> activities = activityService.getAllActivity();

        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        map.put("ac",activities);
        PrintJson.printJsonObj(response,map);
    }

    @PostMapping("clue/saveClueRelation.do")
    public void saveClueRelation(HttpServletRequest request, HttpServletResponse response){
        /*
            如果前端传入的是一个json字符串，怎么取出多个数据呢？
            System.out.println(id);

            {"activityId":"f83c573c757943178719839e075c7ca1","clueId":"5fd91afcef924ec2b6213b1b04440cd8"}
            如何取出数据呢？
        */

        Boolean flag =false;

        String activity[] = request.getParameterValues("activityId");
        String clueId = request.getParameter("clueId");

        // 组装
        for(String activityId : activity){
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setActivityId(activityId);
            clueActivityRelation.setClueId(clueId);
            clueActivityRelation.setId(UUIDUtil.getUUID());
            flag = clueActivityRelationService.saveRelation(clueActivityRelation);
        }

        PrintJson.printJsonFlag(response,flag);
    }

    @GetMapping("clue/queryActivityByWord.do")
    public void queryActivityByWord(HttpServletRequest request, HttpServletResponse response) {
        String queryDate = request.getParameter("queryDate");
        List<Activity> activities = new ArrayList<>();
        if (queryDate.equals("")){
            activities = activityService.getAllActivity();
            PrintJson.printJsonObj(response,activities);
        }else{
            // 这里要使用模糊查询，同时要排除已经关联过的市场活动
            activities = activityService.getActivityByWord(queryDate);
            PrintJson.printJsonObj(response,activities);
        }
    }

    @GetMapping("clue/convert.do")
    public void convert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id =request.getParameter("clueId");
        Clue clue = clueService.getDetail(id);
        request.setAttribute("clue",clue);
        request.getRequestDispatcher("/workbench/clue/convert.jsp").forward(request,response);
    }
}
