package com.hugh.crm.service.impl;

import com.hugh.crm.dao.ActivityDao;
import com.hugh.crm.dao.ActivityRemarkDao;
import com.hugh.crm.pojo.Activity;
import com.hugh.crm.pojo.ListResult;
import com.hugh.crm.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    ActivityDao activityDao;
    @Autowired
    ActivityRemarkDao activityRemarkDao;

    @Override
    public Integer saveActivity(Activity activity) {
        // 修改成自定义异常的形式
        Integer num =activityDao.saveActivity(activity);
        return num;
    }


    @Override
    public ListResult<Activity> pageList(Map<String, Object> activityMap) {
        List<Activity> activitiesList = activityDao.getActivityListByCondition(activityMap);
        Integer total = activityDao.getTotalByCondition(activityMap);



        /* 可以使用Map进行参数的传递
            Map<"String",Object> activityResult = new HashMap<String,Object>();
            activityResult.put("",);
            activityResult.put("",);
            PrintJson.printJsonObj(response,activityResult);
         */

        // 但是因为每个模块都会有分页查询，所以在这里使用POJO来传递参数，同时使用泛型方便各个模块调用
        ListResult<Activity> activityListResult = new ListResult<Activity>();
        activityListResult.setTotal(total);
        activityListResult.setListReuslt(activitiesList);
        return activityListResult;
    }

    @Override
    public Boolean deleteActivity(String id) {
        Boolean flag = false;
        
        /* 传入数组时执行以下程序
        Integer num1 = activityRemarkDao.getActivityRemarkCountById(id);
        Integer num2 = activityRemarkDao.deleteActivityRemark(id);
        if (num1.equals(num2)){
            Integer count = activityDao.deleteActivity(id);
            if (count == 1){
                flag = true;
            }
        }
         */

        Integer count = activityDao.deleteActivity(id);
        if (count == 1){
            flag = true;
        }
        return flag;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Activity getActivityById(String id) {
        Activity activity = activityDao.getActivityById(id);
        return activity;
    }

    @Override
    public Integer updateActivity(Activity activity) {
        Integer count = 0;
        try {
            count = activityDao.updateActivity(activity);
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Activity getById(String id){
        Activity activity = activityDao.getById(id);
        return activity;
    }
}
