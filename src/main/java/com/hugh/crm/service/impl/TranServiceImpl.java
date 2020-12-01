package com.hugh.crm.service.impl;

import com.hugh.crm.dao.*;
import com.hugh.crm.pojo.*;
import com.hugh.crm.service.TranService;
import com.hugh.crm.util.DateTimeUtil;
import com.hugh.crm.util.UUIDUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.omg.CORBA.TRANSACTION_MODE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TranServiceImpl implements TranService {

    @Autowired
    TranDao tranDao;
    @Autowired
    TranCustomerDao tranCustomerDao;
    @Autowired
    ContactsDao contactsDao;
    @Autowired
    CustomerDao customerDao;
    @Autowired
    TranHistoryDao tranHistoryDao;

    @Override
    public List<Tran> getAllTran(){
        List<Tran> trans = tranDao.queryAllTran();
        return trans;
    }

    @Override
    public Tran getDetailById(String id) {
        Tran tran = tranDao.getTranById(id);
        return tran;
    }

    @Override
    public Integer saveTranCustomer(String id, String customerId, String tranId) {
        Integer count = tranCustomerDao.saveTranCustomer(id, customerId, tranId);
        return count;
    }

    @Override
    public TranEdit getTranEditById(String id) {
        TranEdit tran  = tranDao.getTranEditById(id);
        return tran;
    }

    @Override
    public List<Contacts> getALLContact() {
        List<Contacts> contacts = contactsDao.getAllContacts();
        return contacts;
    }

    @Override
    public Integer editTran(Tran tran) {
        Integer count = tranDao.updateTranById(tran);
        return count;
    }

    @Override
    public List<String> getCustomerByContent(String query) {
        List<String> customerNames = customerDao.getCustomerByContent(query);
        return customerNames;
    }

    @Override
    @Transactional
    public Boolean saveTran(Tran tran, String customerName) {
        Boolean flag = false;
        /* 用户注册的方法1
        List<Customer> customers = customerDao.queryAllCustomer();
        List<String> customersNameList = new ArrayList<>();
        for (Customer customer : customers){
            customersNameList.add(customer.getName());
        }
        if (customersNameList.contains(customerName)){
           Integer index = customersNameList.indexOf(customerName);
           tran.setCustomerId(customers.get(index).getId());
        }else{
            Customer newCustomer = new Customer();
            String newCustomerId = UUIDUtil.getUUID();
            newCustomer.setId(newCustomerId);
            newCustomer.setName(customerName);
            newCustomer.setOwner(tran.getOwner());
            newCustomer.setCreateTime(tran.getCreateTime());
            customerDao.saveCustomer(newCustomer);
            tran.setCustomerId(newCustomerId);
        }
         */
        // 用户注册的方法2, 根据customerName对表tbl_customer进行查询，查询是否为null
        Customer customer = customerDao.queryCustomerByName(customerName);
        if (customer != null){
            tran.setCustomerId(customer.getId());
        }else {
            Customer newCustomer = new Customer();
            String newCustomerId = UUIDUtil.getUUID();
            newCustomer.setId(newCustomerId);
            newCustomer.setName(customerName);
            newCustomer.setOwner(tran.getOwner());
            newCustomer.setCreateTime(tran.getCreateTime());
            newCustomer.setNextContactTime(tran.getNextContactTime());
            customerDao.saveCustomer(newCustomer);
            tran.setCustomerId(newCustomerId);
        }

        //创建交易
        tranDao.saveTran(tran);

        // 创建交易历史
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());
        tranHistory.setCreateBy(tran.getCreateBy());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistoryDao.addTranHistory(tranHistory);

        return true;
    }


}
