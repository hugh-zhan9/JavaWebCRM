package com.hugh.crm.dao;
import com.hugh.crm.pojo.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    Integer saveActivity(Activity activity);

    Integer getTotalByCondition(Map<String,Object> activityMap);

    List<Activity> getActivityListByCondition(Map<String,Object> activityMap);

    Integer deleteActivity(String id);

    Activity getActivityById(String id);

    Integer updateActivity(Activity activity);

    Activity getById(String id);
}
