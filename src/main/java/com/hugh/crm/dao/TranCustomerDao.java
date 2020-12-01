package com.hugh.crm.dao;

import org.apache.ibatis.annotations.Param;

public interface TranCustomerDao {
    Integer saveTranCustomer(@Param("id") String id, @Param("customerId") String customerId, @Param("tranId") String tranId);
}
