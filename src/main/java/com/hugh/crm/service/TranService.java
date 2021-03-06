package com.hugh.crm.service;

import com.hugh.crm.pojo.*;

import java.util.List;

public interface TranService {
    List<Tran> getAllTran();

    Tran getDetailById(String id);

    Integer saveTranCustomer(String id, String customerId, String tranId);

    TranEdit getTranEditById(String id);

    List<Contacts> getALLContact();

    Integer editTran(Tran tran);

    List<String> getCustomerByContent(String query);

    Boolean saveTran(Tran tran, String customerName);

    List<TranHistory> getTranHistoryById(String id);
}
