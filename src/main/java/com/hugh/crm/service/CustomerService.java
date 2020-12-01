package com.hugh.crm.service;

import com.hugh.crm.pojo.Contacts;
import com.hugh.crm.pojo.Customer;
import com.hugh.crm.pojo.Tran;

import java.util.List;

public interface CustomerService {
    Integer saveCustomer(Customer customer);

    List<Customer> getAllCustomer();

    Customer getCustomerById(String id);

    List<Tran> getBoundTranById(String id);

    List<Contacts> getBoundContactsById(String id);
}
