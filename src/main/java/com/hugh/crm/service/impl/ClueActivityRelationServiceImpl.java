package com.hugh.crm.service.impl;

import com.hugh.crm.dao.ClueActivityRelationDao;
import com.hugh.crm.pojo.Activity;
import com.hugh.crm.pojo.ClueActivityRelation;
import com.hugh.crm.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {
    @Autowired
    ClueActivityRelationDao clueActivityRelationDao;

    @Override
    public List<Activity> getActivityByClueId(String clueId) {
        List<Activity> activity = clueActivityRelationDao.getActivityByClueId(clueId);
        return activity;
    }

    @Override
    public Boolean deleteClueActivityById(String id) {
        Boolean flag =true;
        Integer count = clueActivityRelationDao.deleteClueActivityById(id);
        if (count != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Boolean saveRelation(ClueActivityRelation clueActivityRelation) {
        Boolean flag = true;
        Integer count = clueActivityRelationDao.saveClueActivity(clueActivityRelation);
        if (count != 1){
            flag = false;
        }
        return flag;
    }
}
