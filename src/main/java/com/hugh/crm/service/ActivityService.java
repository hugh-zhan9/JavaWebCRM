package com.hugh.crm.service;

import com.hugh.crm.pojo.Activity;
import com.hugh.crm.pojo.ListResult;
import com.hugh.crm.pojo.PageListSearch;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    Integer saveActivity(Activity activity);

    ListResult<Activity> pageList(Map<String, Object> activityMap);

    Boolean deleteActivity(String id);

    Activity getActivityById(String id);

    Integer updateActivity(Activity activity);

    Activity getById(String id);

    List<Activity> getAllActivity();

    List<Activity> getActivityByWord(String queryDate);
}
