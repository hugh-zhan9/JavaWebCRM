package com.hugh.crm.controller;

import com.hugh.crm.pojo.*;
import com.hugh.crm.service.*;
import com.hugh.crm.util.DateTimeUtil;
import com.hugh.crm.util.PrintJson;
import com.hugh.crm.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    CustomerService customerService;
    @Autowired
    ContactsService contactsService;
    @Autowired
    TranService tranService;


    @PostMapping("clue/addClue.do")
    public void createClue(@RequestBody Clue clue, HttpServletRequest request, HttpServletResponse response){
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

    @GetMapping("clue/getAllBoundActivity.do")
    public void getAllBoundActivity(HttpServletRequest request, HttpServletResponse response){
        String clueId = request.getParameter("clueId");
        List<Activity> activities = activityService.getAllBoundActivity(clueId);
        PrintJson.printJsonObj(response,activities);
    }

    @GetMapping("clue/getBoundActivity.do")
    public void getBoundActivity(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        String clueId = request.getParameter("clueId");
        List<Activity> activities = activityService.getBoundActivityByName(name,clueId);
        PrintJson.printJsonObj(response,activities);
    }

    @PostMapping("clue/convertToTran.do")
    public void saveClueAsTran(@RequestBody Convert convert, HttpServletResponse response){
        Boolean flag =null;
        String owner = convert.getOwner();
        String money = convert.getMoney();
        String name = convert.getName();
        String expectedDate = convert.getExpectedDate();
        String stage = convert.getStage();
        String source = convert.getSource();
        String activityId = convert.getActivityId();
        String createBy = convert.getCreateBy();
        String createTime = convert.getCreateTime();
        String editBy = convert.getEditBy();
        String editTime  = convert.getEditTime ();
        String description = convert.getDescription();
        String contactSummary = convert.getContactSummary();
        String nextContactTime = convert.getNextContactTime();
        String fullname = convert.getFullname();
        String appellation = convert.getAppellation();
        String company = convert.getCompany();
        String job = convert.getJob();
        String email = convert.getEmail();
        String phone = convert.getPhone();
        String website = convert.getWebsite();
        String mphone = convert.getMphone();
        String address = convert.getAddress();

        Tran tran = new Tran();
        Customer customer = new Customer();
        Contacts contact = new Contacts();

        String contactId = UUIDUtil.getUUID();
        String customerId = UUIDUtil.getUUID();
        String tranId = UUIDUtil.getUUID();

        // 组装tran
        tran.setId(tranId);
        tran.setOwner(owner);
        tran.setMoney(money);
        tran.setName(name);
        tran.setExpectedDate(expectedDate);
        tran.setCustomerId(customerId);
        tran.setStage(stage);
        tran.setSource(source);
        tran.setActivityId(activityId);
        tran.setContactsId(contactId);
        tran.setCreateBy(createBy);
        tran.setCreateTime(createTime);
        tran.setEditBy(editBy);
        tran.setEditTime(editTime);
        tran.setDescription(description);
        tran.setContactSummary(contactSummary);
        tran.setNextContactTime(nextContactTime);


        // 组装customer
        customer.setId(customerId);
        customer.setOwner(owner);
        customer.setName(company);
        customer.setWebsite(website);
        customer.setPhone(phone);
        customer.setCreateBy(createBy);
        customer.setCreateTime(createTime);
        customer.setEditBy(editBy);
        customer.setEditTime(editTime);
        customer.setContactSummary(contactSummary);
        customer.setNextContactTime(nextContactTime);
        customer.setDescription(description);
        customer.setAddress(address);


        // 组装contact
        contact.setId(contactId);
        contact.setOwner(owner);
        contact.setSource(source);
        contact.setCustomerId(customerId);
        contact.setFullname(fullname);
        contact.setAppellation(appellation);
        contact.setEmail(email);
        contact.setMphone(mphone);
        contact.setJob(job);
        contact.setCreateBy(createBy);
        contact.setCreateTime(createTime);
        contact.setEditBy(editBy);
        contact.setEditTime(editTime);
        contact.setDescription(description);
        contact.setContactSummary(contactSummary);
        contact.setNextContactTime(nextContactTime);
        contact.setAddress(address);

        clueService.saveClueAsTran(tran);
        customerService.saveCustomer(customer);
        contactsService.saveContact(contact);
        tranService.saveTranCustomer(UUIDUtil.getUUID(), customerId, tranId);
        clueService.saveContactsActivity(UUIDUtil.getUUID(), contactId, activityId);

        PrintJson.printJsonFlag(response,true);

    }

    @PostMapping("clue/convertToCustomerAndContacts.do")
    @Transactional
    public void convertToCustomerAndContacts(@RequestBody Clue clue, HttpServletResponse response){
        String fullname = clue.getFullname();
        String appellation = clue.getAppellation();
        String owner = clue.getOwner();
        String company = clue.getCompany();
        String job = clue.getJob();
        String email = clue.getEmail();
        String phone = clue.getPhone();
        String website = clue.getWebsite();
        String mphone = clue.getMphone();
        String source = clue.getSource();
        String createBy = clue.getCreateBy();
        String createTime = clue.getCreateTime();
        String editBy = clue.getEditBy();
        String editTime = clue.getEditTime();
        String description = clue.getDescription();
        String contactSummary = clue.getContactSummary();
        String nextContactTime = clue.getNextContactTime();
        String address = clue.getAddress();

        Customer customer = new Customer();
        Contacts contact = new Contacts();

        // 组装customer
        String customerId = UUIDUtil.getUUID();
        customer.setId(customerId);
        customer.setOwner(owner);
        customer.setName(company);
        customer.setWebsite(website);
        customer.setPhone(phone);
        customer.setCreateBy(createBy);
        customer.setCreateTime(createTime);
        customer.setEditBy(editBy);
        customer.setEditTime(editTime);
        customer.setContactSummary(contactSummary);
        customer.setNextContactTime(nextContactTime);
        customer.setDescription(description);
        customer.setAddress(address);


        // 组装contact
        String contactId = UUIDUtil.getUUID();
        contact.setId(contactId);
        contact.setOwner(owner);
        contact.setSource(source);
        contact.setCustomerId(customerId);
        contact.setFullname(fullname);
        contact.setAppellation(appellation);
        contact.setEmail(email);
        contact.setMphone(mphone);
        contact.setJob(job);
        contact.setCreateBy(createBy);
        contact.setCreateTime(createTime);
        contact.setEditBy(editBy);
        contact.setEditTime(editTime);
        contact.setDescription(description);
        contact.setContactSummary(contactSummary);
        contact.setNextContactTime(nextContactTime);
        contact.setAddress(address);


        customerService.saveCustomer(customer);
        contactsService.saveContact(contact);

        PrintJson.printJsonFlag(response,true);
    }

}
