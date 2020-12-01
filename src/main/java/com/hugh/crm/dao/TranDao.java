package com.hugh.crm.dao;

import com.hugh.crm.pojo.Tran;
import com.hugh.crm.pojo.TranEdit;

import java.util.List;

public interface TranDao {
    Integer saveTran(Tran tran);

    List<Tran> queryAllTran();

    List<Tran> getBoundTranById(String id);

    Tran getTranById(String id);

    Integer updateTranById(Tran tran);

    TranEdit getTranEditById(String id);
}
