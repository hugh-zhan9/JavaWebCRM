package com.hugh.crm.controller;

import com.hugh.crm.pojo.Contacts;
import com.hugh.crm.service.ContactsService;
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
public class ContactsController {
    @Autowired
    ContactsService contactsService;

    @GetMapping("contacts/queryAllContacts.do")
    public void queryAllContacts(HttpServletResponse response){
        List<Contacts> contacts = contactsService.getAllContacts();
        PrintJson.printJsonObj(response,contacts);
    }

    @GetMapping("contacts/detail.do")
    public void showDetail(String id, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        Contacts contact = contactsService.getContactsById(id);
        request.setAttribute("contact",contact);
        request.getRequestDispatcher("/workbench/contacts/detail.jsp").forward(request, response);
    }
}
