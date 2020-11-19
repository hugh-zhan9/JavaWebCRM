package com.hugh.crm.dao;

import com.hugh.crm.pojo.DicType;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DicTypeDao {
    List<DicType> getAllDicType();
}
