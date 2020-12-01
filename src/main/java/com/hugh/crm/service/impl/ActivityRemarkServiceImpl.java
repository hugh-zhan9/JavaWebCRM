package com.hugh.crm.service.impl;

import com.hugh.crm.dao.ActivityDao;
import com.hugh.crm.dao.ActivityRemarkDao;
import com.hugh.crm.pojo.ActivityRemark;
import com.hugh.crm.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Autowired
    ActivityRemarkDao activityRemarkDao;
    @Autowired
    ActivityDao activityDao;


    // private ActivityRemarkDao activityRemarkDao = MybatisUtil.getSqlSession().getMapper(ActivityRemarkDao.class);

    @Override
    public List<ActivityRemark> getActivityRemarkById(String id) {
        List<ActivityRemark> activityRemarks = activityRemarkDao.getActivityRemarkById(id);
        return activityRemarks;
    }

    @Override
    public Boolean saveNoteContent(ActivityRemark activityRemark) {
        Boolean flag =true;
        Integer count = activityRemarkDao.saveActivityRemarkContent(activityRemark);
        if (count != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    @Transactional
    public Boolean deleteActivityAndRemark(String activityId) {

        Integer count1 = activityRemarkDao.getActivityRemarkCountById(activityId);
        Integer count2 = activityRemarkDao.deleteActivityRemark(activityId);
        Integer count3 = activityDao.deleteActivity(activityId);
        Boolean flag = true;
        if (count1==count2 && count3==1){
            flag = true;
        }else{
            flag = false;
        }
        return flag;
    }
}
