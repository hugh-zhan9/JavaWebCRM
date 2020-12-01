package com.hugh.crm.service.impl;

import com.hugh.crm.dao.ContactsDao;
import com.hugh.crm.dao.CustomerDao;
import com.hugh.crm.dao.TranDao;
import com.hugh.crm.pojo.Contacts;
import com.hugh.crm.pojo.Customer;
import com.hugh.crm.pojo.Tran;
import com.hugh.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDao customerDao;
    @Autowired
    TranDao tranDao;
    @Autowired
    ContactsDao contactsDao;

    @Override
    public Integer saveCustomer(Customer customer) {
        customerDao.saveCustomer(customer);
        return 1;
    }

    @Override
    public List<Customer> getAllCustomer() {
        List<Customer> customers = customerDao.queryAllCustomer();
        return customers;
    }

    @Override
    public Customer getCustomerById(String id) {
        Customer customer = customerDao.queryCustomerById(id);
        return customer;
    }

    @Override
    public List<Tran> getBoundTranById(String id) {
        List<Tran> trans = tranDao.getBoundTranById(id);
        return trans;
    }

    @Override
    public List<Contacts> getBoundContactsById(String id) {
        List<Contacts> contacts = contactsDao.getBoundContactsById(id);
        return contacts;
    }


}
