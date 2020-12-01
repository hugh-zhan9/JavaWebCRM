package com.hugh.crm.controller;

import com.hugh.crm.pojo.Contacts;
import com.hugh.crm.pojo.Customer;
import com.hugh.crm.pojo.Tran;
import com.hugh.crm.service.CustomerService;
import com.hugh.crm.util.PrintJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("customer/queryAllCustomer.do")
    public void getAllCustomer(HttpServletResponse response){
        List<Customer> customers = customerService.getAllCustomer();
        PrintJson.printJsonObj(response,customers);
    }

    @GetMapping("customer/detail.do")
    public void getCustomerById(String id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = customerService.getCustomerById(id);
        request.setAttribute("customer",customer);
        request.getRequestDispatcher("/workbench/customer/detail.jsp").forward(request, response);
    }

    @GetMapping("customer/queryBoundTran.do")
    public void getBoundTranById(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        List<Tran> trans = customerService.getBoundTranById(id);
        PrintJson.printJsonObj(response,trans);
    }

    @GetMapping("customer/queryBoundContacts.do")
    public void getBoundContactsById(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        List<Contacts> contacts = customerService.getBoundContactsById(id);
        PrintJson.printJsonObj(response,contacts);
    }
}
