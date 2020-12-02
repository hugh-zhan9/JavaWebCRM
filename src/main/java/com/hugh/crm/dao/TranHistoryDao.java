package com.hugh.crm.dao;

import com.hugh.crm.pojo.TranHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranHistoryDao {
    Integer addTranHistory(TranHistory tranHistory);

    List<TranHistory> getTranHistoryById(String id);
}
