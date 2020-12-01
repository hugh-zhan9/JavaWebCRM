package com.hugh.crm.dao;

import com.hugh.crm.pojo.Activity;
import org.apache.ibatis.annotations.Param;

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

    List<Activity> getAllActivity();

    List<Activity> getActivityListByName(String queryDate);

    /*
        浏览器报错：Parameter 'XXX' not found. Available parameters are [arg1, arg0, param1,...
        解决方案:@Param注解，多变量MyBatis不知道怎么分配值。
     */
    List<Activity> getBoundActivityByKey(@Param("name") String name, @Param("clueId") String clueId);

    List<Activity> getAllBoundActivity(String clueId);
}
