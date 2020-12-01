package com.hugh.crm.dao;

import com.hugh.crm.pojo.TranHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface TranHistoryDao {
    Integer addTranHistory(TranHistory tranHistory);
}
