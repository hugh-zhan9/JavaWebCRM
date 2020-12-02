package com.hugh.crm.controller;

import com.hugh.crm.pojo.*;
import com.hugh.crm.service.TranService;
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
import java.util.List;

@Controller
public class TranController {
    @Autowired
    TranService tranService;

    @GetMapping("tran/tranAllContacts.do")
    public void getAllTran(HttpServletResponse response){
        List<Tran> trans = tranService.getAllTran();
        PrintJson.printJsonObj(response,trans);
    }

    @GetMapping("tran/detail.do")
    public void getCustomerById(String id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Tran tran = tranService.getDetailById(id);
        request.setAttribute("tran",tran);
        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request, response);
    }

    /**
        修改按钮执行的操作
     */
    @GetMapping("tran/editTranById.do")
    public void editTranById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        TranEdit tran = tranService.getTranEditById(id);
        if (tran != null){
            request.setAttribute("tran",tran);
            request.getRequestDispatcher("/workbench/transaction/edit.jsp").forward(request, response);
        }else{
            throw new Exception();
        }
    }

    @GetMapping("tran/getAllContacts.do")
    public void getAllContacts(HttpServletResponse response){
        List<Contacts> contacts = tranService.getALLContact();
        PrintJson.printJsonObj(response,contacts);
    }

    /**
        更新按钮执行的操作
     */
    @PostMapping("tran/editTran.do")
    public void editTran(@RequestBody Tran tran, HttpServletRequest request, HttpServletResponse response){
        Boolean flag = false;

        tran.setEditTime(DateTimeUtil.getSysTime());

        // 执行数据库操作
        Integer count = tranService.editTran(tran);
        if (count==1){
            flag = true;
        }else {
            flag = false;
        }
        PrintJson.printJsonFlag(response, flag);
    }



    @GetMapping("tran/getAccountName.do")
    public void getAccountName(HttpServletRequest request, HttpServletResponse response){
        String query = request.getParameter("name");
            List<String> customerNames = tranService.getCustomerByContent(query);
        PrintJson.printJsonObj(response,customerNames);
    }

    @PostMapping("tran/save.do")
    public void saveNewTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("新的请求");

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String money = request.getParameter("money");
        String name = request.getParameter("name");
        String expectedDate = request.getParameter("expectedDate");
        String customerName = request.getParameter("customerName");
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String percent = request.getParameter("percent");
        String source = request.getParameter("source");
        String activityId = request.getParameter("activityId");
        String contactsId = request.getParameter("contactsId");
        Users users = (Users) request.getSession().getAttribute("user");
        String createBy = users.getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");

        Tran tran = new Tran();

        tran.setId(id);
        tran.setOwner(owner);
        tran.setMoney(money);
        tran.setName(name);
        tran.setExpectedDate(expectedDate);
        tran.setStage(stage);
        tran.setType(type);
        tran.setPercent(percent);
        tran.setSource(source);
        tran.setActivityId(activityId);
        tran.setContactsId(contactsId);
        tran.setCreateBy(createBy);
        tran.setCreateTime(createTime);
        tran.setDescription(description);
        tran.setContactSummary(contactSummary);
        tran.setNextContactTime(nextContactTime);

        Boolean flag = tranService.saveTran(tran,customerName);

        if (flag){
            response.sendRedirect(request.getContextPath()+"/workbench/transaction/index.jsp");

            /* 这里不能用请求转发，否则路径没有变化，刷新页面会重复添加值
            request.getRequestDispatcher("/workbench/transaction/index.jsp").forward(request,response);
             */
        }
    }

    @GetMapping("tran/getTranHistoryById.do")
    public void getTranHistoryById(HttpServletRequest request, HttpServletResponse response){
        String tranId = request.getParameter("id");
        List<TranHistory> histories = tranService.getTranHistoryById(tranId);
        PrintJson.printJsonObj(response, histories);
    }
}
