package com.hugh.crm.dao;

import com.hugh.crm.pojo.DicValue;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DicValueDao {
    List<DicValue> getValueByTypeCode(String typeCode);
}
