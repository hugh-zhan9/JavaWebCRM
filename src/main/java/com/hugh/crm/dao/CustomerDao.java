package com.hugh.crm.dao;

import com.hugh.crm.pojo.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDao {
    Integer saveCustomer(Customer customer);

    List<Customer> queryAllCustomer();

    Customer queryCustomerById(String id);

    Customer queryCustomerByName(String name);

    List<String> getCustomerByContent(String query);
}
